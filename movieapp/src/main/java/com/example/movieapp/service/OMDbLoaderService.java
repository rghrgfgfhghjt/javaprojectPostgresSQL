package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.example.movieapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OMDbLoaderService {

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String OMDB_API_KEY = "your_api_key";
    private static final String OMDB_URL = "http://www.omdbapi.com/?apikey=%s&type=movie&s=year&y=%d&page=%d";

    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    @Scheduled(fixedRate = 3 * 60 * 60 * 1000) // каждые 3 часа
    public void loadMovies() {
        int year = currentYear--;
        for (int page = 1; page <= 5; page++) {
            String url = String.format(OMDB_URL, OMDB_API_KEY, year, page);
            try {
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);
                if (response == null || !"True".equals(response.get("Response"))) continue;

                List<Map<String, String>> movies = (List<Map<String, String>>) response.get("Search");
                if (movies == null) continue;

                for (Map<String, String> item : movies) {
                    String title = item.get("Title");
                    int movieYear = Integer.parseInt(item.get("Year").replaceAll("[^0-9]", ""));
                    if (!movieRepository.existsByTitleAndYear(title, movieYear)) {
                        movieRepository.save(new Movie(null, title, movieYear));
                    }
                }

            } catch (Exception e) {
                log.error("Failed to load movies from OMDb", e);
            }
        }
    }
}
