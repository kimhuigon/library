package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer>{

  
}
