package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PinRequestDto(@NotBlank @Size(min = 3) String title,
                            @NotBlank String description,
                            @NotBlank String imageUrl) { }
