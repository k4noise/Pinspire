package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.BoardResponseDto;
import com.k4noise.pinspire.domain.BoardEntity;
import org.mapstruct.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BoardMapper {
    @Transactional(propagation = Propagation.MANDATORY)
    public abstract BoardResponseDto entityToResponse(BoardEntity board);
    public List<BoardResponseDto> entitiesToResponse(List<BoardEntity> boards) {
        return boards.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}