package com.example.movieapp.service;

import com.example.movieapp.dto.UpdateUserRequest;
import com.example.movieapp.dto.UserResponse;
import com.example.movieapp.model.Role;
import com.example.movieapp.model.User;
import com.example.movieapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getCurrentUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    public void updateUser(User user, UpdateUserRequest updateRequest) {
        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getName() != null) {
            user.setName(updateRequest.getName());
        }
        userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(User currentUser, Long targetUserId) {
        if (!currentUser.getId().equals(targetUserId) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Forbidden");
        }
        userRepository.deleteById(targetUserId);
    }

    @Cacheable("users")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user ->
                UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .name(user.getName())
                        .build()
        ).toList();
    }
}
