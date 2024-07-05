package com.example.library.entity;  // 패키지명은 프로젝트 구조에 맞게 조정하세요

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdDate;

    public CommentDTO(Long id, String content, String author, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}