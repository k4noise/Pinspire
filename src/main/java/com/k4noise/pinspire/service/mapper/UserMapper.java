package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.UserResponseDto;
import com.k4noise.pinspire.domain.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserResponseDto entityToResponse(UserEntity user);
    public List<UserResponseDto> entitiesToResponse(List<UserEntity> users) {
        return users.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}
