package com.devproject.usersystem.services.implementations;

import com.devproject.usersystem.data.models.Comment;
import com.devproject.usersystem.data.models.ImageModel;
import com.devproject.usersystem.data.models.Profile;
import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.data.repositories.ProfileRepository;
import com.devproject.usersystem.services.interfaces.CommentService;
import com.devproject.usersystem.services.interfaces.ImageService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.interfaces.SongService;
import com.devproject.usersystem.services.models.ProfileServiceModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileSongsDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileUpdateModel;
import com.devproject.usersystem.web.view.models.songs.SongUploadModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ModelMapper modelMapper;
    private final ProfileRepository profileRepository;

    private final ImageService imageService;
    private final CommentService commentService;
    @Autowired
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    private SongService songService;

    public ProfileServiceImpl(ModelMapper modelMapper, ProfileRepository profileRepository, ImageService imageService, CommentService commentService) {
        this.modelMapper = modelMapper;
        this.profileRepository = profileRepository;

        this.imageService = imageService;
        this.commentService = commentService;
    }

    public Profile initProfile(String username) {
        Profile profile = new Profile();
        profile.setUsername(username);
        Song song = new Song();
        song.setName("Song name");
        song.setArtist("Song artist");
        song.setSongUrl("Song url");
        Path profilePicPath = Paths.get("src\\main\\resources\\static\\img\\placeholderProfile.jpg");

        try {
            ImageModel profilePic = new ImageModel("file", "jpeg", Files.readAllBytes(profilePicPath));
            profile.setProfilePicture(profilePic);
            songService.saveSong(song);

        }
        catch(IOException e){
            //TODO handle exception
        }

        profile.setTop1song(this.initSong());
        profile.setTop2song(this.initSong());
        profile.setTop3song(this.initSong());
        profileRepository.save(profile);
        return profile;
    }

    @Override
    public ProfileDisplayModel getProfileByUsername(String username) {
        Profile profile = profileRepository.getByUsername(username);
        ProfileDisplayModel model = modelMapper.map(profile, ProfileDisplayModel.class);
        if(profile.getGender()!=null) {
            model.setGender(profile.getGender().name());
        }
        return model;
    }
    public ProfileSongsDisplayModel getProfileSongs(String username){
        return modelMapper.map(profileRepository.getByUsername(username), ProfileSongsDisplayModel.class);

    }
    public void uploadSongToProfile(String username, SongUploadModel songUploadModel, MultipartFile file) throws IOException {

        Profile profile = profileRepository.getByUsername(username);
        profile.getSongs().add(songService.uploadSong(songUploadModel,file));
        profileRepository.save(profile);


    }

    @Override
    public void editProfile(String name, ProfileUpdateModel profileModel, MultipartFile image) throws IOException {
        Profile profile = profileRepository.getByUsername(name);
        ProfileServiceModel model = modelMapper.map(profileModel,ProfileServiceModel.class);
        if(!model.getFirstName().equals(profile.getFirstName())){
            profile.setFirstName(model.getFirstName());
        }
        if(!model.getLastName().equals(profile.getLastName())){
            profile.setLastName(model.getLastName());
        }
        if(!model.getCountry().equals(profile.getCountry())){
            profile.setCountry(model.getCountry());
        }
        if(model.getGender()!=profile.getGender()){
            profile.setGender(model.getGender());
        }
        if(model.getAge()!=profile.getAge()){
            profile.setAge(model.getAge());
        }
        if(!model.getDescription().equals(profile.getDescription())){
            profile.setDescription(model.getDescription());
        }
        if(!image.isEmpty()){

            ImageModel img = profile.getProfilePicture();
            profile.setProfilePicture(imageService.saveImage(image));
            profileRepository.save(profile);
            imageService.delete(img);
        }
        profileRepository.save(profile);

    }

    @Override
    public void deleteSongFromProfile(String username, Long id) throws Exception {
        Profile profile = profileRepository.getByUsername(username);
        Song song = songService.getByIdReturnsSong(id);
        profile.getSongs().remove(song);
        profileRepository.save(profile);
        songService.deleteSong(song);
    }

    @Override
    public List<ProfileDisplayModel> getAllProfiles() {
        return profileRepository.findAll().stream().map(p -> modelMapper.map(p,ProfileDisplayModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProfileDisplayModel> getAllProfilesExceptCurrentAndFriends(String username) {
        System.out.println();
        List<Profile>profiles = profileRepository.findAll();
        Profile current = profileRepository.getByUsername(username);
        profiles.remove(current);
        current.getFriendRequestsSent().forEach(profiles::remove);
        current.getFriends().forEach(profiles::remove);

        return profiles.stream().map(p -> modelMapper.map(p, ProfileDisplayModel.class)).collect(Collectors.toList());

    }



    @Override
    @Transactional
    public void addFriendRequestToProfile(Long id, String name) {


        Profile profileToSendTo = profileRepository.findById(id).orElseThrow();
        Profile profileSending = profileRepository.getByUsername(name);
        if(profileToSendTo.getFriendRequestsSent().contains(profileSending)){
            this.acceptFriendRequestToProfile(id,name);
            return;
        }
        profileSending.getFriendRequestsSent().add(profileToSendTo);
        profileToSendTo.getFriendRequestsReceived().add(profileSending);
        profileRepository.save(profileToSendTo);
        profileRepository.save(profileSending);
    }


    @Override
    public List<ProfileDisplayModel> getFriendRequestsSentForProfile(String name) {
        return profileRepository.getByUsername(name).getFriendRequestsSent().stream()
                .map(p->modelMapper.map(p,ProfileDisplayModel.class))
                .collect(Collectors.toList());
    }
    public List<ProfileDisplayModel> getFriendRequestsReceivedForProfile(String name) {
        return profileRepository.getByUsername(name).getFriendRequestsReceived().stream()
                .map(p->modelMapper.map(p,ProfileDisplayModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileDisplayModel> getFriendsForProfile(String name) {
        return profileRepository.getByUsername(name).getFriends().stream()
                .map(p -> modelMapper.map(p,ProfileDisplayModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void declineFriendRequestToProfile(Long requestId, String profileName) {
        Profile requestProfile = profileRepository.findById(requestId).orElseThrow();
        Profile targetProfile = profileRepository.getByUsername(profileName);
        requestProfile.getFriendRequestsSent().remove(targetProfile);
        targetProfile.getFriendRequestsReceived().remove(requestProfile);
        profileRepository.save(requestProfile);
        profileRepository.save(targetProfile);
    }

    @Override
    @Transactional
    public void removeFriendFromProfile(Long friendId, String name) {
        Profile friendProfile = profileRepository.findById(friendId).orElseThrow();
        Profile targetProfile = profileRepository.getByUsername(name);
        friendProfile.getFriends().remove(targetProfile);
        targetProfile.getFriends().remove(friendProfile);
        profileRepository.save(friendProfile);
        profileRepository.save(targetProfile);
    }

    @Override
    public void uploadTop1Song(SongUploadModel songModel, String username, MultipartFile picture) throws IOException {
        Profile profile = profileRepository.getByUsername(username);
        Song song = profile.getTop1song();
        editSong(songModel, picture, song);
        songService.saveSong(song);

    }

    @Override
    public void uploadTop2Song(SongUploadModel songModel, String username, MultipartFile picture) throws IOException {
        Profile profile = profileRepository.getByUsername(username);
        Song song = profile.getTop2song();
        editSong(songModel, picture, song);
        songService.saveSong(song);

    }


    @Override
    public void uploadTop3Song(SongUploadModel songModel, String username, MultipartFile picture) throws IOException {
        Profile profile = profileRepository.getByUsername(username);
        Song song = profile.getTop3song();
        editSong(songModel, picture, song);
        songService.saveSong(song);

    }

    @Override
    public Comment addSongCommentToProfile(Song song, String authorProfileName, String content) {

        Profile profile = profileRepository.getByUsername(authorProfileName);
        Comment comment = commentService.createComment(song, profile, content);
        profile.getComments().add(comment);
        profileRepository.save(profile);
        return comment;
    }

    @Override
    public void deleteCommentFromProfile(String username, Long id) {

        Profile profile = profileRepository.getByUsername(username);
        profile.getComments().remove(commentService.getCommentById(id));
        profileRepository.save(profile);
    }

    private void editSong(SongUploadModel songModel, MultipartFile picture, Song song) throws IOException {
        if(!songModel.getName().equals(song.getName())){
            song.setName(songModel.getName());
        }
        if(!songModel.getArtist().equals(song.getArtist())){
            song.setArtist(songModel.getArtist());
        }
        if(!songModel.getSongUrl().equals(song.getSongUrl())){
            song.setSongUrl(songModel.getSongUrl());
        }
        System.out.println();
        if(!picture.isEmpty()){
            ImageModel oldImage = song.getImage();
            song.setImage(imageService.saveImage(picture));
            songService.saveSong(song);
            imageService.delete(oldImage);
        }
    }



    @Override
    @Transactional
    public void acceptFriendRequestToProfile(Long id, String name) {
        Profile targetProfile = profileRepository.getByUsername(name);
        Profile requestProfile = profileRepository.findById(id).orElseThrow();
        targetProfile.getFriends().add(requestProfile);
        requestProfile.getFriends().add(targetProfile);
        requestProfile.getFriendRequestsSent().remove(targetProfile);
        targetProfile.getFriendRequestsReceived().remove(requestProfile);
        profileRepository.save(targetProfile);
        profileRepository.save(requestProfile);
    }

    private Song initSong(){
        Song song = new Song();
        song.setName("Song name");
        song.setArtist("Song artist");
        song.setSongUrl("Song URL");
        Path songImagePath = Paths.get("src\\main\\resources\\static\\img\\music-placeholder.png");
        try {
            ImageModel songImage = new ImageModel("file", "png", Files.readAllBytes(songImagePath));
            song.setImage(songImage);
            songService.saveSong(song);

        }
        catch(IOException e){
            //TODO handle exception
        }
        return song;
    }



}
