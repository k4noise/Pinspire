package com.k4noise.pinspire.adapter.web.dto.response;

import java.time.LocalDateTime;

public record PinResponseDto(Long id, String title, String description, String imageUrl, LocalDateTime uploadedAt, Long userId, Long boardId, int comments, int likes) { }
