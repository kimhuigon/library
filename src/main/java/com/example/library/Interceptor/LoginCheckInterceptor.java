// package com.example.library.Interceptor;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.web.servlet.HandlerInterceptor;

// public class LoginCheckInterceptor implements HandlerInterceptor {
//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//         // 세션에서 사용자 정보를 확인합니다.
//         Object user = request.getSession().getAttribute("user");
        
//         if (user == null) {
//             // 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트합니다.
//             response.sendRedirect("/login");
//             return false;
//         }
        
//         return true;
//     }
// }
