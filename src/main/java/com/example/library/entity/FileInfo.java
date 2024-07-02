package com.example.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class FileInfo {
  @Id @GeneratedValue
  Integer id;

  String originalName;

  String saveName;

  @ManyToOne
  Board board;
}
