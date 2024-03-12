package com.example.fullstack_backend.service;

import com.example.fullstack_backend.model.Comment;
import com.example.fullstack_backend.model.Task;
import com.example.fullstack_backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment createComment(Long taskId, Comment comment) {
        comment.setTask(new Task(taskId));
        comment.setCreatedDate(new Date());
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, Comment updatedComment) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isPresent()) {
            Comment commentToUpdate = existingComment.get();
            commentToUpdate.setText(updatedComment.getText());
            return commentRepository.save(commentToUpdate);
        }
        return null;
    }

    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }
}
