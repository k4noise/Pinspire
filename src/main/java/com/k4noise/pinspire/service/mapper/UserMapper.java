package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.UserResponseDto;
import com.k4noise.pinspire.domain.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Transactional(propagation = Propagation.MANDATORY)
    public abstract UserResponseDto entityToResponse(UserEntity dish);
    public List<UserResponseDto> entitiesToResponse(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}
