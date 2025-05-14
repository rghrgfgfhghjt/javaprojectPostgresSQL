package com.example.movieapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private Integer year;
}
