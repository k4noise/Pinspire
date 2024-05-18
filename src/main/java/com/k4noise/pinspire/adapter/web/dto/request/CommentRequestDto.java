package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(@NotBlank String text) { }
