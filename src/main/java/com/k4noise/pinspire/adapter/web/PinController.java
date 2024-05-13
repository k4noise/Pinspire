package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.request.PinRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.PinResponseDto;
import com.k4noise.pinspire.service.PinService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{userId}")
    public List<PinResponseDto> getPinsByUser(@PathVariable Long userId) {
        return pinService.getPinsByUser(userId);
    }

    @GetMapping("/board/{boardId}")
    public List<PinResponseDto> getPinsByBoard(@PathVariable Long boardId) {
        return pinService.getPinsByBoard(boardId);
    }

    @PostMapping("/{boardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PinResponseDto createPin(@PathVariable Long boardId, @AuthenticationPrincipal UserDetails user, @Valid @RequestBody PinRequestDto pinDto) {
        return pinService.createPin(user, boardId, pinDto);
    }

    @PutMapping("/{id}")
    public PinResponseDto updatePin(@PathVariable Long id, @AuthenticationPrincipal UserDetails user, @Valid @RequestBody PinRequestDto pinDto) {
        return pinService.updatePin(user, id, pinDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePin(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        pinService.deletePin(user, id);
    }
}
