package com.example.movieapp.controller;

import com.example.movieapp.dto.UpdateUserRequest;
import com.example.movieapp.dto.UserResponse;
import com.example.movieapp.model.User;
import com.example.movieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getCurrentUser(user);
    }

    @PutMapping("/me")
    public void updateUser(@AuthenticationPrincipal User user, @RequestBody UpdateUserRequest updateRequest) {
        userService.updateUser(user, updateRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@AuthenticationPrincipal User currentUser, @PathVariable Long userId) {
        userService.deleteUser(currentUser, userId);
    }
}
