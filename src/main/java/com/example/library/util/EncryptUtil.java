package com.example.library.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {
  public String encode(String raw) {
    MessageDigest md;
    String hex = "";
    try {
      md = MessageDigest.getInstance("SHA-256");
      md.update(raw.getBytes());
      hex = String.format("%064x", new BigInteger(1, md.digest()));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hex;
  }

}
