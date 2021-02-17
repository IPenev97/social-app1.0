package com.devproject.usersystem.services.interfaces;

import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.web.view.models.songs.AddCommentToSongModel;
import com.devproject.usersystem.web.view.models.songs.SongDisplayModel;
import com.devproject.usersystem.web.view.models.songs.SongUpdateModel;
import com.devproject.usersystem.web.view.models.songs.SongUploadModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SongService {
    Song uploadSong(SongUploadModel model, MultipartFile image) throws IOException;

    SongDisplayModel findById(Long id) throws Exception;

    void updateSong(SongUpdateModel model, MultipartFile image) throws Exception;
    void deleteSong(Song id);
    Song getByIdReturnsSong(Long id);
    public void saveSong(Song song);


    void addCommentToSong(String authorName, AddCommentToSongModel addCommentToSongModel);

    void deleteCommentFromSong(Long songId, Long commentId);
}
