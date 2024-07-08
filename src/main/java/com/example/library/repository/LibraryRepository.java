package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.library.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer>{
    // 이름 또는 시도로 검색
    List<Library> findByNameContainingIgnoreCaseOrSidoContainingIgnoreCase(String name, String sido);

    // 위치 기반으로 도서관 검색
    @Query(value = "SELECT * FROM library WHERE (lng - :lng) * (lng - :lng) + (lat - :lat) * (lat - :lat) <= (:distance / 111.32) * (:distance / 111.32)", nativeQuery = true)
    List<Library> findLibrariesWithinDistance(@Param("lat") double lat, @Param("lng") double lng, @Param("distance") double distance);

    // 이름 또는 시도로 검색 (위치 정보 없음)
    @Query(value = "SELECT * FROM library WHERE name LIKE '%' || :query || '%' OR sido LIKE '%' || :query || '%'", nativeQuery = true)
    List<Library> findByQuery(@Param("query") String query);
}
