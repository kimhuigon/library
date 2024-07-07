package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.library.entity.Post;
import com.example.library.entity.Comment;
import com.example.library.entity.CommentDTO;
import com.example.library.entity.User;
import com.example.library.exception.PostNotFound;
import com.example.library.service.PostService;
import com.example.library.service.CommentService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

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
    model.addAttribute("comments", commentService.findByPostId(id));
    return "posts/detail";
  }

  @GetMapping("/new")
  @ResponseBody
  public ResponseEntity<String> createForm(HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
    }
    return ResponseEntity.ok("/posts/create");
  }

  @GetMapping("/create")
  public String showCreateForm(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return "redirect:/login";
    }
    model.addAttribute("post", new Post());
    return "posts/create";
  }

  @PostMapping
  public String create(@Valid @ModelAttribute Post post, BindingResult bindingResult, HttpSession session,
      Model model) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return "redirect:/login";
    }

    if (bindingResult.hasErrors()) {
      return "posts/create";
    }

    post.setAuthor(user.getName());
    postService.save(post);
    return "redirect:/posts";
  }

  @GetMapping("/edit/{id}")
  @ResponseBody
  public ResponseEntity<?> editForm(@PathVariable Long id, HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
    }

    Post post = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
    if (!post.getAuthor().equals(user.getName())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to edit this post");
    }

    return ResponseEntity.ok("/posts/edit-page/" + id);
  }

  @GetMapping("/edit-page/{id}")
  public String editPage(@PathVariable Long id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return "redirect:/login";
    }

    Post post = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
    if (!post.getAuthor().equals(user.getName())) {
      model.addAttribute("errorMessage", "You don't have permission to edit this post.");
      return "error";
    }

    model.addAttribute("post", post);
    return "posts/edit";
  }

  @PostMapping("/update/{id}")
  public String update(@PathVariable Long id, @ModelAttribute Post post, HttpSession session, Model model) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return "redirect:/login";
    }

    Post existingPost = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
    if (!existingPost.getAuthor().equals(user.getName())) {
      model.addAttribute("errorMessage", "You don't have permission to update this post.");
      return "error";
    }

    existingPost.setTitle(post.getTitle());
    existingPost.setContent(post.getContent());
    postService.save(existingPost);
    return "redirect:/posts/" + id;
  }

  @DeleteMapping("/delete/{id}")
  @ResponseBody
  public ResponseEntity<String> delete(@PathVariable Long id, HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
    }

    Post post = postService.findById(id).orElseThrow(() -> new PostNotFound(id));
    if (!post.getAuthor().equals(user.getName())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to delete this post");
    }

    postService.deleteById(id);
    return ResponseEntity.ok("success");
  }

  @PostMapping("/{postId}/comments")
  @ResponseBody
  public ResponseEntity<?> addComment(@PathVariable Long postId, @RequestBody Comment comment,
      HttpSession session) {
    try {
      User user = getSessionUser(session);
      Post post = postService.findById(postId).orElseThrow(() -> new PostNotFound(postId));
      comment.setPost(post);
      comment.setAuthor(user.getName());
      comment.setCreatedDate(LocalDateTime.now());
      Comment savedComment = commentService.save(comment);

      CommentDTO commentDTO = new CommentDTO(
          savedComment.getId(),
          savedComment.getContent(),
          savedComment.getAuthor(),
          savedComment.getCreatedDate());

      return ResponseEntity.ok(commentDTO);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: " + e.getMessage());
    }
  }

  @GetMapping("/{postId}/comments")
  @ResponseBody
  public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
    List<Comment> comments = commentService.findByPostId(postId);
    return ResponseEntity.ok(comments);
  }

  private User getSessionUser(HttpSession session) {
    User user = (User) session.getAttribute("user_info");
    if (user == null) {
      throw new IllegalStateException("로그인이 필요합니다.");
    }
    return user;
  }
}
