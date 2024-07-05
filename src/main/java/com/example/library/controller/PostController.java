package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Comment;


import com.example.library.entity.Post;
import com.example.library.entity.User;
import com.example.library.exception.PostNotFound;

import com.example.library.service.PostService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public String list(Model model, 
                       @RequestParam(defaultValue = "0") int page, 
                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postService.findAll(pageable);
        model.addAttribute("postPage", postPage);
        model.addAttribute("pageNumbers", getPageNumbers(postPage));
        return "posts/list";
    }

    private List<Integer> getPageNumbers(Page<Post> postPage) {
        int totalPages = postPage.getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }
        return List.of();
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        User user = getSessionUser(session);
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping
    public String create(@ModelAttribute Post post, HttpSession session) {
        User user = getSessionUser(session);
        post.setAuthor(user.getName());
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = getSessionUser(session);
        Post post = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
        if (!post.getAuthor().equals(user.getName())) {
            throw new IllegalStateException("권한이 없습니다.");
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Post post, HttpSession session) {
        User user = getSessionUser(session);
        Post existingPost = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
        if (!existingPost.getAuthor().equals(user.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        post.setId(id);
        post.setAuthor(user.getName());
        postService.save(post);
        return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id, HttpSession session) {
        User user = getSessionUser(session);
        Post post = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
        if (!post.getAuthor().equals(user.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        postService.deleteById(id);
        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }

    private User getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user_info");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return user;
    }
}
    