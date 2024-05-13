package com.k4noise.pinspire.adapter.web.dto.response;

import java.util.List;

public record BoardResponseDto(Long id, String name, String description, Long userId, List<Long> pinIds) { }
