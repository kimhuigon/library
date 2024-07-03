package com.example.library.service;

import com.example.library.entity.Library;
import com.example.library.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    // 이름 또는 시도로 도서관 검색 (위치 정보 없음)
    public List<Library> searchLibraries(String query) {
        return libraryRepository.findByQuery(query);
    }

    // 모든 도서관 검색
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    // 위치 기반으로 도서관 검색
    public List<Library> getNearbyLibraries(double lat, double lng, double distance) {
        try {
            return libraryRepository.findLibrariesWithinDistance(lat, lng, distance);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching nearby libraries", e);
        }
    }
}
