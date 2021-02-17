package com.devproject.usersystem.web.view.models.songs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCommentToSongModel {
    private String username;
    private Long songId;
    private String content;
}
