package com.example.movieapp.service;

import com.example.movieapp.dto.MovieResponse;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.User;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public List<MovieResponse> getAllMovies(int page, int size) {
        return movieRepository.findAll(PageRequest.of(page, size))
                .stream().map(this::toDto).toList();
    }

    public void addToFavorites(User user, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        user.getFavoriteMovies().add(movie);
        userRepository.save(user);
    }

    public void removeFromFavorites(User user, Long movieId) {
        user.getFavoriteMovies().removeIf(movie -> movie.getId().equals(movieId));
        userRepository.save(user);
    }

    public List<MovieResponse> getMoviesNotInFavorites(User user, String loaderType) {
        if (loaderType.equals("sql")) {
            return getMoviesNotInFavoritesSql(user.getId());
        } else {
            return getMoviesNotInFavoritesInMemory(user);
        }
    }

    private List<MovieResponse> getMoviesNotInFavoritesSql(Long userId) {
        String jpql = """ 
            SELECT m FROM Movie m WHERE m.id NOT IN (
                SELECT f.id FROM User u JOIN u.favoriteMovies f WHERE u.id = :userId
            )
        """;
        TypedQuery<Movie> query = entityManager.createQuery(jpql, Movie.class);
        query.setParameter("userId", userId);
        return query.getResultList().stream().map(this::toDto).toList();
    }

    private List<MovieResponse> getMoviesNotInFavoritesInMemory(User user) {
        Set<Long> favoriteIds = user.getFavoriteMovies().stream()
                .map(Movie::getId).collect(Collectors.toSet());

        return movieRepository.findAll().stream()
                .filter(movie -> !favoriteIds.contains(movie.getId()))
                .map(this::toDto)
                .toList();
    }

    private MovieResponse toDto(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .build();
    }
}
