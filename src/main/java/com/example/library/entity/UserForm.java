package com.example.library.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForm {
  @NotEmpty(message = "아이디를 입력해주세요")
  private String userId;
  @NotEmpty(message = "비밀번호를 입력해주세요")
  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
  private String password;
  @NotEmpty(message = "비밀번호가 일치하지 않습니다")
  private String password_confirm;
  @NotEmpty(message = "회원 이름을 입력해주세요")
  private String name;
  @NotEmpty(message = "이메일을 입력해주세요")
  @Email(message = "이메일 형식이 올바르지 않습니다")
  private String email;

  @Builder
  public UserForm(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public User toEntity() {
    User user = new User();
    user.setUserId(userId);
    user.setPassword(password);
    user.setName(name);
    user.setEmail(email);
    return user;
  }
}
