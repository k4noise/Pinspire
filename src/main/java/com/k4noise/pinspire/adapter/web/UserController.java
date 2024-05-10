package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.UserRequestDto;
import com.k4noise.pinspire.adapter.web.dto.UserResponseDto;
import com.k4noise.pinspire.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResponseDto updateUser(@PathVariable Long id, Principal principal, @Valid @RequestBody UserRequestDto userDto) {
        return service.updateUser(principal, id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, Principal principal) {
        service.deleteUser(principal, id);
    }
}