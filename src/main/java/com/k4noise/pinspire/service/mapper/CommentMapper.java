package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.CommentResponseDto;
import com.k4noise.pinspire.domain.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Transactional(propagation = Propagation.MANDATORY)
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pin.id", target = "pinId")
    public abstract CommentResponseDto entityToResponse(CommentEntity comment);
    public List<CommentResponseDto> entitiesToResponse(List<CommentEntity> comment) {
        return comment.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}