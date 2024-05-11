package com.k4noise.pinspire.adapter.web.dto.response;

import com.k4noise.pinspire.domain.CommentEntity;
import com.k4noise.pinspire.domain.LikeEntity;

import java.time.LocalDateTime;
import java.util.List;

public record PinResponseDto(Long id, String title, String description, String imageUrl, LocalDateTime uploadedAt, UserResponseDto user, Long boardId, List<CommentResponseDto> comments, List<LikeResponseDto> likes) { }
