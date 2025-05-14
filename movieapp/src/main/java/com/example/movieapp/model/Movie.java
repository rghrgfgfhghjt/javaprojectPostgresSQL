package com.example.movieapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "year"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer year;
}
