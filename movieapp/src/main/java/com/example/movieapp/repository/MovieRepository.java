package com.example.movieapp.repository;

import com.example.movieapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitleAndYear(String title, Integer year);

    Page<Movie> findAll(Pageable pageable);

    boolean existsByTitleAndYear(String title, int movieYear);
}
