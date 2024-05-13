package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.BoardResponseDto;
import com.k4noise.pinspire.domain.BoardEntity;
import com.k4noise.pinspire.domain.PinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BoardMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "pins", target = "pinIds", qualifiedByName = "pinListToIdList")
    public abstract BoardResponseDto entityToResponse(BoardEntity board);

    public abstract List<BoardResponseDto> entitiesToResponse(List<BoardEntity> boards);

    @Named("pinListToIdList")
    List<Long> pinListToIdList(List<PinEntity> pins) {
        if (pins == null) {
            return Collections.emptyList();
        }
        return pins.stream()
                .map(PinEntity::getId)
                .collect(Collectors.toList());
    }
}