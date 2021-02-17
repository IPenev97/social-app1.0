package com.devproject.usersystem.web.view.models.songs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SongSuggestionModel {
    private String name;
    private String artist;
    private String songUrl;
    private String username;
}
