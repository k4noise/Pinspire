package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.request.CommentRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.CommentResponseDto;
import com.k4noise.pinspire.service.CommentService;
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
@RequestMapping("/api/v1/comment")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Validated
public class CommentController {
    CommentService commentService;

    @GetMapping("/{id}")
    public CommentResponseDto getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/user/{id}")
    public List<CommentResponseDto> getCommentsByUser(@PathVariable Long id) {
        return commentService.getCommentsByUser(id);
    }

    @GetMapping("/pin/{id}")
    public List<CommentResponseDto> getCommentsByPin(@PathVariable Long id) {
        return commentService.getCommentsByPin(id);
    }

    @PostMapping
    public CommentResponseDto createComment(Principal principal, @Valid CommentRequestDto commentDto) {
        return commentService.createComment(principal, commentDto);
    }

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, Principal principal, @Valid CommentRequestDto commentDto) {
        return commentService.updateComment(principal, id, commentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id, Principal principal) {
        commentService.deleteComment(principal, id);
    }
}
