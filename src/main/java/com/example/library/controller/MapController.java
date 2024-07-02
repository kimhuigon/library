package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.library.repository.LibraryRepository;

@Controller
public class MapController {

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping("/map")
    public String map(Model model) {
        // 도서관 목록을 모델에 추가
        model.addAttribute("libraries", libraryRepository.findAll());
        return "map";
    }
}
