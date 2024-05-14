package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardRequestDto(@NotBlank @Size(min = 3) String name,
                              @NotBlank String description) { }
