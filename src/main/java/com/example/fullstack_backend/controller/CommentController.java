package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.Comment;
import com.example.fullstack_backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/task/{taskId}")
    public List<Comment> getCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }

    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId).orElse(null);
    }

    @PostMapping("/task/{taskId}")
    public Comment createComment(@PathVariable Long taskId, @RequestBody Comment comment) {
        return commentService.createComment(taskId, comment);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        return commentService.updateComment(commentId, updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public boolean deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
