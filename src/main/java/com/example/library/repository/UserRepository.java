package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
  public User findByUserIdAndPassword(String userId, String password);

}
