package com.example.library.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comment {
  @Id @GeneratedValue
  Integer id;

  String text;

  LocalDateTime createDate;

  @ManyToOne
  Board board;
}
