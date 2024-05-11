package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.request.LikeRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.LikeResponseDto;
import com.k4noise.pinspire.service.LikeService;
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
@RequestMapping("/api/v1/like")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Validated
public class LikeController {
    LikeService likeService;

    @GetMapping("/{id}")
    public LikeResponseDto getLikeById(@PathVariable Long id) {
        return likeService.getLikeById(id);
    }

    @GetMapping("/user/{id}")
    public List<LikeResponseDto> getLikesByUser(@PathVariable Long id) {
        return likeService.getLikesByUser(id);
    }

    @GetMapping("/pin/{id}")
    public List<LikeResponseDto> getLikesByPin(@PathVariable Long id) {
        return likeService.getLikesByPin(id);
    }

    @PostMapping
    public LikeResponseDto createLike(Principal principal, @Valid LikeRequestDto likeDto) {
        return likeService.createLike(principal, likeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long id, Principal principal) {
        likeService.deleteLike(principal, id);
    }
}
