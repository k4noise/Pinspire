package com.k4noise.pinspire.adapter.web.dto.request;

import com.k4noise.pinspire.adapter.web.validation.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(@NotBlank @Size(min = 3) String username,
                             @NotBlank @Email String email,
                             @PasswordConstraint String password,
                             String displayName) { }

