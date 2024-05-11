package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, String displayName) { }

