package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.LikeResponseDto;
import com.k4noise.pinspire.domain.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class LikeMapper {
    @Transactional(propagation = Propagation.MANDATORY)
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pin.id", target = "pinId")
    public abstract LikeResponseDto entityToResponse(LikeEntity like);
    public List<LikeResponseDto> entitiesToResponse(List<LikeEntity> likes) {
        return likes.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}