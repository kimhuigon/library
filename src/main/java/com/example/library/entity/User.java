package com.example.library.entity;


import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User implements Serializable {
  @Id
  private String userId; // 회원아이디
  private String password; // 회원비밀번호
  private String name; // 회원이름
  private String email; // 회원이메일
}
