package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.library.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Integer>{
    List<Library> findByNameContainingIgnoreCase(String name);
    @Query(value = "SELECT * FROM library WHERE " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(lat)) * cos(radians(lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(lat)))) <= :distance", 
           nativeQuery = true)
    List<Library> findLibrariesWithinDistance(@Param("lat") double lat, @Param("lng") double lng, @Param("distance") double distanceKm);
  
}
