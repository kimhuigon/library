package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Board;
import com.example.library.entity.FileInfo;
import java.util.List;


public interface FileInfoRepository 
    extends JpaRepository<FileInfo, Integer> {
  List<FileInfo> findByBoard(Board board);
}
