package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Comment;

public interface CommentRepository 
    extends JpaRepository<Comment, Integer> {
  
}
