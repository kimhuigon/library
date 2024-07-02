package com.example.library.email;


import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
  @Override
  protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(
        "계정명@gmail.com", "앱비밀번호");
  }
}