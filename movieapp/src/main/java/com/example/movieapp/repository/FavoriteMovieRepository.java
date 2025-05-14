package com.example.movieapp.repository;

import com.example.movieapp.model.FavoriteMovie;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

    List<FavoriteMovie> findAllByUser(User user);

    boolean existsByUserAndMovie(User user, Movie movie);

    Optional<FavoriteMovie> findByUserAndMovie(User user, Movie movie);
}
