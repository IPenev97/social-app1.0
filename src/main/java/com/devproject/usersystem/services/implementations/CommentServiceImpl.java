package com.devproject.usersystem.services.implementations;

import com.devproject.usersystem.data.models.Comment;
import com.devproject.usersystem.data.models.Profile;
import com.devproject.usersystem.data.models.Song;
import com.devproject.usersystem.data.repositories.CommentRepository;
import com.devproject.usersystem.services.interfaces.CommentService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import com.devproject.usersystem.services.interfaces.SongService;
import com.devproject.usersystem.web.view.models.comments.CommentWebModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;


        this.modelMapper = modelMapper;
    }


    @Override
    public Comment createComment(Song song, Profile profile, String content) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String[] dateAndTime = dtf.format(now).split("\\s+");
        Comment comment = new Comment();
        comment.setSong(song);
        comment.setAuthor(profile);
        comment.setContent(content);
        comment.setDate(dateAndTime[0]);
        comment.setTime(dateAndTime[1]);
        return commentRepository.save(comment);

    }

    @Override
    public CommentWebModel getCommentWebModelById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        CommentWebModel commentWebModel = modelMapper.map(comment,CommentWebModel.class);
        commentWebModel.setSongId(comment.getSong().getId());
        return commentWebModel;
    }
    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public void updateComment(CommentWebModel commentModel) {
        Comment comment = commentRepository.findById(commentModel.getId()).orElseThrow();
        comment.setContent(commentModel.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        System.out.println();
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setAuthor(null);
        commentRepository.delete(comment);
    }
}
