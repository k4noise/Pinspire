package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PinRequestDto(@NotBlank String title, @NotBlank String description, @NotBlank String imageUrl) { }
