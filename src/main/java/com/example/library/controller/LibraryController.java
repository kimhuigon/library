package com.example.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.library.entity.Library;
import com.example.library.repository.LibraryRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class LibraryController {
  @Autowired LibraryRepository libraryRepository;
  @GetMapping("/library")
  public String library(Model model) {
    List<Library> list = libraryRepository.findAll();

    log.error("" + list.size());

    model.addAttribute("list", list);
      return "library";
  }

  @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/libraries")
    @ResponseBody
    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    @GetMapping("/api/libraries/search")
    @ResponseBody
    public List<Library> searchLibraries(@RequestParam String query) {
        return libraryRepository.findByNameContainingIgnoreCase(query);
    }

    @GetMapping("/api/libraries/nearby")
    @ResponseBody
    public List<Library> getNearbyLibraries(@RequestParam double lat, @RequestParam double lng, @RequestParam(defaultValue = "5") double distance) {
        return libraryRepository.findLibrariesWithinDistance(lat, lng, distance);
    }


}
