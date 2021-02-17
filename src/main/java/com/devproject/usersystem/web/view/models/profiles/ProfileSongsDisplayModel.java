package com.devproject.usersystem.web.view.models.profiles;

import com.devproject.usersystem.data.models.ImageModel;
import com.devproject.usersystem.data.models.Song;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProfileSongsDisplayModel {
    private String username;
    private List<Song>songs;
    private Song top1song;
    private Song top2song;
    private Song top3song;
    private ImageModel profilePicture;
    private String firstName;
    private String lastName;
}
