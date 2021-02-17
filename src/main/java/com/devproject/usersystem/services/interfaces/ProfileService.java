package com.devproject.usersystem.services.interfaces;

import com.devproject.usersystem.data.models.Comment;
import com.devproject.usersystem.data.models.Profile;
import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.web.view.models.profiles.ProfileSongsDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileUpdateModel;
import com.devproject.usersystem.web.view.models.songs.SongUploadModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfileService {
    ProfileDisplayModel getProfileByUsername(String username);
    Profile initProfile(String username);
    ProfileSongsDisplayModel getProfileSongs(String username);
    void uploadSongToProfile(String username, SongUploadModel songModel, MultipartFile file) throws IOException;


    void editProfile(String name, ProfileUpdateModel profileModel, MultipartFile file) throws IOException;
    void deleteSongFromProfile(String username, Long id) throws Exception;
    List<ProfileDisplayModel> getAllProfiles();
    List<ProfileDisplayModel> getAllProfilesExceptCurrentAndFriends(String username);

    void acceptFriendRequestToProfile(Long id, String name);

    void addFriendRequestToProfile(Long id, String name);

    List<ProfileDisplayModel> getFriendRequestsSentForProfile(String name);
    List<ProfileDisplayModel> getFriendRequestsReceivedForProfile(String name);

    List<ProfileDisplayModel> getFriendsForProfile(String name);

    void declineFriendRequestToProfile(Long requestId, String profileName);

    void removeFriendFromProfile(Long friendId, String name);

    void uploadTop1Song(SongUploadModel song, String username, MultipartFile picture) throws IOException;

    void uploadTop2Song(SongUploadModel song, String username, MultipartFile picture) throws IOException;

    void uploadTop3Song(SongUploadModel song, String username, MultipartFile picture) throws IOException;

    Comment addSongCommentToProfile(Song song, String commentAuthorName, String content);

    void deleteCommentFromProfile(String username, Long id);
}
