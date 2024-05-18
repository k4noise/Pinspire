package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.request.UserRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.UserResponseDto;
import com.k4noise.pinspire.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Validated
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {
    UserService service;

    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto userDto) {
        return service.registerUser(userDto);
    }

    @PutMapping
    public UserResponseDto updateUser(@AuthenticationPrincipal UserDetails principal, @Valid @RequestBody UserRequestDto userDto) {
        return service.updateUser(principal,userDto);
    }

    @PatchMapping("/avatar")
    public void updateUserAvatar(@AuthenticationPrincipal UserDetails principal, @RequestBody @Valid @NotBlank String avatarUrl) {
        service.updateUserAvatar(principal, avatarUrl);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal UserDetails principal) {
        service.deleteUser(principal);
    }
}