package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.UserRequestDto;
import com.k4noise.pinspire.adapter.web.dto.UserResponseDto;
import com.k4noise.pinspire.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto userDto) {
        return service.registerUser(userDto);
    }

    @PutMapping("{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto userDto) {
        return service.updateUser(id, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}