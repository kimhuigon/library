package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.library.entity.User;
import com.example.library.entity.UserForm;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;
import com.example.library.util.EncryptUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class UserController {
  @Autowired
  UserRepository userRepository;
  @Autowired
  HttpSession session;
  @Autowired
  EncryptUtil encryptUtil;
  @Autowired UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public String loginPost(@ModelAttribute UserForm userForm) {
    User users = userRepository.findByUserIdAndPassword(userForm.getUserId(), encryptUtil.encode(userForm.getPassword()));
    if (users != null) {
      session.setAttribute("user_info", users);
    }

    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout() {
    session.invalidate();
    return "redirect:/";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }

  @PostMapping("/signup")
  public String signupPost(@ModelAttribute @Valid UserForm userForm, BindingResult result, Model model) {
    if(result.hasErrors()) {
      result.getFieldErrors().forEach(error -> {
        model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
      });
      return "signup";
    }

    User user = userForm.toEntity();
    String encodePw = encryptUtil.encode(user.getPassword());
    user.setPassword(encodePw);

    userRepository.save(user);
    return "redirect:/login";
  }

  @GetMapping("/userId/{userId}/exists")
  public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
    return ResponseEntity.ok(userService.checkUserIdDuplicate(userId));
  }
}
