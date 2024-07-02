package com.example.library.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.library.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LogInCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    HttpSession session = request.getSession();
    User User = (User) session.getAttribute("user_info");
    if (User == null) {
      response.sendRedirect("/login");
      return false;
    }
    return true;
  }
}