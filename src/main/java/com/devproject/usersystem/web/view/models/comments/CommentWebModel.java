package com.devproject.usersystem.web.view.models.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentWebModel {
    private Long songId;
    private Long id;
    private String content;
    private String displayProfileUsername;
}
