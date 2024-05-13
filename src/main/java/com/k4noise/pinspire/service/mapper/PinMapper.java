package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.PinResponseDto;
import com.k4noise.pinspire.domain.PinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PinMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "board.id", target = "boardId")
    @Mapping(source = "pin", target = "comments", qualifiedByName = "countComments")
    @Mapping(source = "pin", target = "likes", qualifiedByName = "countLikes")
    public abstract PinResponseDto entityToResponse(PinEntity pin);
    public List<PinResponseDto> entitiesToResponse(List<PinEntity> pins) {
        return pins.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    @Named("countComments")
    int countComments(PinEntity pin) {
        if (pin.getComments() == null) {
            return 0;
        }
        return pin.getComments().size();
    }

    @Named("countLikes")
    int countLikes(PinEntity pin) {
        if (pin.getLikes() == null) {
            return 0;
        }
        return pin.getLikes().size();
    }
}