package com.example.library.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.library.entity.Library;
import com.example.library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MapController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/map")
    public String map(Model model) {
        return "map";
    }

    @GetMapping("/api/libraries")
    @ResponseBody
    public List<Library> getLibraries() {
        return libraryService.getAllLibraries();
    }

    // 도서관 이름 또는 시도로 검색
    @GetMapping("/api/libraries/search")
    @ResponseBody
    public ResponseEntity<?> searchLibraries(@RequestParam String query) {
        try {
            List<Library> libraries = libraryService.searchLibraries(query);
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            log.error("Error searching libraries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching libraries");
        }
    }

    // 현재 위치 기반으로 주변 도서관 검색
    @GetMapping("/api/libraries/nearby")
    @ResponseBody
    public ResponseEntity<?> getNearbyLibraries(@RequestParam double lat, @RequestParam double lng,
            @RequestParam(defaultValue = "2") double distance) {
        try {
            List<Library> libraries = libraryService.getNearbyLibraries(lat, lng, distance);
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            log.error("Error getting nearby libraries", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching nearby libraries");
        }
    }
}
