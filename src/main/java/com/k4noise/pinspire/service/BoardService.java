package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.dto.request.BoardRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.BoardResponseDto;
import com.k4noise.pinspire.domain.BoardEntity;
import com.k4noise.pinspire.adapter.repository.BoardRepository;
import com.k4noise.pinspire.domain.UserEntity;
import com.k4noise.pinspire.service.mapper.BoardMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class BoardService {
    BoardRepository boardRepository;
    UserService userService;
    BoardMapper boardMapper;

    public boolean existsByBoardId(Long id) {
        return boardRepository.existsById(id);
    }

    public BoardResponseDto getBoardById(Long id) {
        return boardMapper.entityToResponse(getBoardEntityById(id));
    }

    public BoardEntity getBoardEntityById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + id));
    }

    public List<BoardResponseDto> getBoardsByUser(Long userId) {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return boardMapper.entitiesToResponse(boardRepository.findByUserId(userId));
    }

    @Transactional
    public BoardResponseDto createBoard(Principal principal, BoardRequestDto boardDto) {
        BoardEntity board = new BoardEntity();
        UserEntity user = userService.getUserEntityById(boardDto.userId());

        if (!Objects.equals(principal.getName(), user.getUsername())) {
            throw new AccessDeniedException("Action with another user is prohibited");
        }

        board.setName(boardDto.name());
        board.setDescription(boardDto.description());
        board.setUser(user);

        BoardEntity savedBoard = boardRepository.save(board);
        log.info("Created board with id {}", board.getId());
        return boardMapper.entityToResponse(savedBoard);
    }

    @Transactional
    public BoardResponseDto updateBoard(Principal principal, Long id, BoardRequestDto boardDto) {
        BoardEntity board = getBoardEntityById(id);
        if (!Objects.equals(principal.getName(), board.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }

        board.setName(boardDto.name());
        board.setDescription(boardDto.description());
        return boardMapper.entityToResponse(boardRepository.save(board));
    }

    @Transactional
    public void deleteBoard(Principal principal, Long id) {
        BoardEntity board = getBoardEntityById(id);
        if (!Objects.equals(principal.getName(), board.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }

        boardRepository.delete(board);
        log.info("Deleted board with id {}", board.getId());
    }
}