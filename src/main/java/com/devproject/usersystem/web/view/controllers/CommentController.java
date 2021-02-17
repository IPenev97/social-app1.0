package com.devproject.usersystem.web.view.controllers;

import com.devproject.usersystem.services.interfaces.CommentService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.interfaces.SongService;
import com.devproject.usersystem.web.view.models.comments.CommentWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final ProfileService profileService;
    private final SongService songService;

    public CommentController(CommentService commentService, ProfileService profileService, SongService songService) {
        this.commentService = commentService;
        this.profileService = profileService;
        this.songService = songService;

    }
    @GetMapping("/{username}/comment/update/{id}")
    @ResponseBody
    public CommentWebModel getComment(@PathVariable(value="username") String username, @PathVariable(value="id") Long id){
        CommentWebModel commentModel = commentService.getCommentWebModelById(id);
        commentModel.setDisplayProfileUsername(username);
        return commentModel;
    }
    @GetMapping("/comment/delete/{username}/{id}")
    public String deleteComment(@PathVariable Long id, @PathVariable String username, Principal principal){
        System.out.println();
        profileService.deleteCommentFromProfile(principal.getName(), id);
        songService.deleteCommentFromSong(commentService.getCommentWebModelById(id).getSongId(), id);


        return "redirect:/music/profile/"+username;
    }
}
