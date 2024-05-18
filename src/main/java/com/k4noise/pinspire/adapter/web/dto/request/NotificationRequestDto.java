package com.k4noise.pinspire.adapter.web.dto.request;

public record NotificationRequestDto(String ownerUsername, String actorUsername, String pinTitle, Long pinId) { }
