package com.example.movieapp.controller;

import com.example.movieapp.dto.MovieResponse;
import com.example.movieapp.model.User;
import com.example.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieResponse> getAllMovies(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "15") int size) {
        return movieService.getAllMovies(page, size);
    }

    @PostMapping("/{movieId}/favorite")
    public void addToFavorites(@AuthenticationPrincipal User user, @PathVariable Long movieId) {
        movieService.addToFavorites(user, movieId);
    }

    @DeleteMapping("/{movieId}/favorite")
    public void removeFromFavorites(@AuthenticationPrincipal User user, @PathVariable Long movieId) {
        movieService.removeFromFavorites(user, movieId);
    }

    @GetMapping("/not-in-favorites")
    public List<MovieResponse> getMoviesNotInFavorites(@AuthenticationPrincipal User user,
                                                       @RequestParam String loaderType) {
        return movieService.getMoviesNotInFavorites(user, loaderType);
    }
}
