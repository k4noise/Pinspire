package com.k4noise.pinspire.service.mapper;

import com.k4noise.pinspire.adapter.web.dto.response.PinResponseDto;
import com.k4noise.pinspire.domain.PinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PinMapper {
    @Transactional(propagation = Propagation.MANDATORY)
    @Mapping(source = "pin.id", target = "pinId")
    public abstract PinResponseDto entityToResponse(PinEntity pin);
    public List<PinResponseDto> entitiesToResponse(List<PinEntity> pins) {
        return pins.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}