package com.example.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.repository.UserRepository;

@Service
public class UserService {
  @Autowired UserRepository userRepository;
  public boolean checkUserIdDuplicate(String userId) {
    return userRepository.existsById(userId);
  }
}
