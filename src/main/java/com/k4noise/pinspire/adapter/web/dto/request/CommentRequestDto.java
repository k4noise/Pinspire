package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequestDto(@NotBlank String text) { }
