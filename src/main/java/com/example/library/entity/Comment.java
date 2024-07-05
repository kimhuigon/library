package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    private String content;
    private String author;
    private LocalDateTime createdDate = LocalDateTime.now();

    // 명시적으로 setter 메서드 추가
    public void setPost(Post post) {
        this.post = post;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}