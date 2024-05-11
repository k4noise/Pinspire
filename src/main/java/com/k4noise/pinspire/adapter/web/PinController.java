package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.request.PinRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.PinResponseDto;
import com.k4noise.pinspire.service.PinService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/pin")
@Validated
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PinController {
    PinService pinService;

    @GetMapping("/{id}")
    public PinResponseDto getPin(@PathVariable Long id) {
        return pinService.getPinById(id);
    }

    @GetMapping("/user/{id}")
    public List<PinResponseDto> getPinsByUser(@PathVariable Long id) {
        return pinService.getPinsByUser(id);
    }

    @GetMapping("/board/{id}")
    public List<PinResponseDto> getPinsByBoard(@PathVariable Long id) {
        return pinService.getPinsByBoard(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PinResponseDto createPin(Principal principal, @Valid PinRequestDto pinDto) {
        return pinService.createPin(principal, pinDto);
    }

    @PutMapping("/{id}")
    public PinResponseDto updatePin(@PathVariable Long id, Principal principal, @Valid PinRequestDto pinDto) {
        return pinService.updatePin(principal, id, pinDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePin(@PathVariable Long id, Principal principal) {
        pinService.deletePin(principal, id);
    }
}
