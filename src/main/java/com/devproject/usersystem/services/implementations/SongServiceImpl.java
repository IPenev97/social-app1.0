package com.devproject.usersystem.services.implementations;

import com.devproject.usersystem.data.models.Comment;
import com.devproject.usersystem.data.models.ImageModel;
import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.data.repositories.SongRepository;
import com.devproject.usersystem.services.interfaces.CommentService;
import com.devproject.usersystem.services.interfaces.ImageService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.interfaces.SongService;
import com.devproject.usersystem.web.view.models.songs.AddCommentToSongModel;
import com.devproject.usersystem.web.view.models.songs.SongDisplayModel;
import com.devproject.usersystem.web.view.models.songs.SongUpdateModel;
import com.devproject.usersystem.web.view.models.songs.SongUploadModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SongServiceImpl implements SongService {
    private final CommentService commentService;
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private ProfileService profileService;
    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public SongServiceImpl(CommentService commentService, SongRepository songRepository, ModelMapper modelMapper, ImageService imageService) {
        this.commentService = commentService;
        this.songRepository = songRepository;

        this.modelMapper = modelMapper;
        this.imageService = imageService;


    }

    @Override
    public Song uploadSong(SongUploadModel model, MultipartFile image) throws IOException {

            Song song = modelMapper.map(model,Song.class);
            if(image.isEmpty()){
                Path songPlaceholderPath = Paths.get("src\\main\\resources\\static\\img\\music-placeholder.png");
                    song.setImage(imageService.saveImage(Files.readAllBytes(songPlaceholderPath)));
            }
            else {
                song.setImage(imageService.saveImage(image));
            }
            songRepository.save(song);
            return song;

    }


    public SongDisplayModel findById(Long id) throws Exception {

        return songRepository.findById(id).map(song -> modelMapper.map(song, SongDisplayModel.class)).orElseThrow(() -> new Exception("No such song"));
    }

    @Override
    public void updateSong(SongUpdateModel model, MultipartFile image) throws Exception {

        Song song = songRepository.findById(model.getId()).orElseThrow(() -> new Exception("No such song"));
        if(model.getName()!=null) {
            song.setName(model.getName());
        }
        if(model.getArtist()!=null) {
            song.setArtist(model.getArtist());
        }
        if(model.getSongUrl()!=null) {
            song.setSongUrl(model.getSongUrl());
        }
        if(!image.isEmpty()){
            ImageModel img = song.getImage();
            song.setImage(imageService.saveImage(image));
            imageService.delete(img);

        }
        songRepository.save(song);

    }

    @Override
    public void deleteSong(Song song) {
        songRepository.delete(song);

    }

    @Override
    public Song getByIdReturnsSong(Long id) {
        return songRepository.findById(id).orElseThrow();
    }
    public void saveSong(Song song){
        songRepository.save(song);
    }

    @Override
    public void addCommentToSong(String authorProfileName, AddCommentToSongModel commentModel) {


        Song song = songRepository.findById(commentModel.getSongId()).orElseThrow(
                // TODO: AddException
        );
        song.getComments().add(profileService.addSongCommentToProfile (song, authorProfileName, commentModel.getContent()));
        songRepository.save(song);





    }

    @Override
    public void deleteCommentFromSong(Long songId, Long commentId) {

        Song song = songRepository.findById(songId).orElseThrow();
        Comment comment = commentService.getCommentById(commentId);
        song.getComments().remove(comment);
        songRepository.save(song);
    }


}
