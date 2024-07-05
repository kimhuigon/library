package com.example.library.controller;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.jni.FileInfo;
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
  @Autowired
  UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public String loginPost(@ModelAttribute UserForm userForm) {
    User users = userRepository.findByUserIdAndPassword(userForm.getUserId(),
        encryptUtil.encode(userForm.getPassword()));
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
    if (result.hasErrors()) {
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

  @GetMapping("/mypage")
  public String mypage(Model model) {
    User user = (User)session.getAttribute("user_info");
    String name = user.getUserId();

    model.addAttribute("userId", name);
    return "mypage";
  }

  @GetMapping("userupdate/{userId}")
  public String userupdate(Model model) {
    User user = (User)session.getAttribute("user_info");
    String name = user.getUserId();

    user.setName(name);

    model.addAttribute("userId", name);
    return "/userupdate";
  }

  @PostMapping("/userupdate/{userId}")
  public String userupdatePost(@ModelAttribute User user, @PathVariable("userId") String userId) {
    user = (User)session.getAttribute("user_info");

    String userid = user.getUserId();
    user.getPassword();
    user.getName();
    user.getEmail();
    userRepository.save(user);

    return "mypage";
  }

  @GetMapping("/delete/{userId}")
  public String boardDelete(@PathVariable("userId") String userId) {
    User user = new User();
    user.setUserId(userId);

    userRepository.deleteById(userId);
    return "redirect:/login";
  }
}
