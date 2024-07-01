package com.example.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Library {
  @Id
  Integer id;
  String name;
  String sido;
  String roaddress;
  String wdaystart;
  String wdayend;
  String sdaystart;
  String sdayend;
  String hdaystart;
  String hdayend;
  Integer seat;
  String lat;
  String lng;
  String tel;
  String homepage;
}
