package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.request.BoardRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.BoardResponseDto;
import com.k4noise.pinspire.service.BoardService;
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
@RequestMapping("api/v1/board")
@Validated
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BoardController {
    BoardService boardService;

    @GetMapping("/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @GetMapping("/user/{id}")
    public List<BoardResponseDto> getBoardsByUser(@PathVariable Long id) {
        return boardService.getBoardsByUser(id);
    }

    @PostMapping()
    public BoardResponseDto createBoard(Principal principal, @Valid BoardRequestDto boardDto) {
        return boardService.createBoard(principal, boardDto);
    }

    @PutMapping("/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, Principal principal, @Valid BoardRequestDto boardDto) {
        return boardService.updateBoard(principal, id, boardDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable Long id, Principal principal) {
        boardService.deleteBoard(principal, id);
    }
}
