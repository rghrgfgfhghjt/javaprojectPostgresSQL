package com.example.movieapp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only Latin letters")
    private String username;

    private String name;

}
