package com.devproject.usersystem.services.interfaces;

import com.devproject.usersystem.data.models.Comment;
import com.devproject.usersystem.data.models.Profile;
import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.web.view.models.comments.CommentWebModel;

public interface CommentService {



    Comment createComment(Song song, Profile profile, String content);

    CommentWebModel getCommentWebModelById(Long id);

    void updateComment(CommentWebModel commentModel);

    void deleteComment(Long id);
    Comment getCommentById(Long id);
}
