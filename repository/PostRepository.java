package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.library.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
