package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.response.LikeResponseDto;
import com.k4noise.pinspire.service.LikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/like")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LikeController {
    LikeService likeService;

    @GetMapping("/{id}")
    public LikeResponseDto getLikeById(@PathVariable Long id) {
        return likeService.getLikeById(id);
    }

    @GetMapping("/user/{userId}")
    public List<LikeResponseDto> getLikesByUser(@PathVariable Long userId) {
        return likeService.getLikesByUser(userId);
    }

    @GetMapping("/pin/{pinId}")
    public List<LikeResponseDto> getLikesByPin(@PathVariable Long pinId) {
        return likeService.getLikesByPin(pinId);
    }

    @PostMapping("/{pinId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLike(@PathVariable Long pinId, @AuthenticationPrincipal UserDetails user) {
        likeService.createLike(user, pinId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        likeService.deleteLike(user, id);
    }
}
