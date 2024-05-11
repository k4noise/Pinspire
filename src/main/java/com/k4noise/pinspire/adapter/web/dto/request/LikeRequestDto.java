package com.k4noise.pinspire.adapter.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record LikeRequestDto(@NotNull Long userId, @NotNull Long pinId) { }
