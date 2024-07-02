package com.example.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
  List<Board> findByTitleContainingOrContentContaining(
    String search, String search2);

  Page<Board> findByTitleContainingOrContentContaining(
    String search, String search2, Pageable pageable);
}