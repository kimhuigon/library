package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.library.entity.Comment;
import com.example.library.entity.User;
import com.example.library.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user_info");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
        }

        Comment comment = commentService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        if (!comment.getAuthor().equals(user.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to delete this comment");
        }

        commentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}