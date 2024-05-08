package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.BoardEntity;
import com.k4noise.pinspire.adapter.repository.BoardRepository;
import com.k4noise.pinspire.domain.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class BoardService {
    private BoardRepository boardRepository;
    UserService userService;

    public boolean existsByBoardId(Long id) {
        return boardRepository.existsById(id);
    }

    public List<BoardEntity> getAllBoards() {
        return boardRepository.findAll();
    }

    public BoardEntity getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + id));
    }

    public List<BoardEntity> getBoardsByUser(Long userId) {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return boardRepository.findByUserId(userId);
    }

    @Transactional
    public BoardEntity createBoard(BoardEntity board, Long userId) {
        UserEntity user = userService.getUserById(userId);
        board.setUser(user);

        log.info("Created board with id {}", board.getId());
        return boardRepository.save(board);
    }

    @Transactional
    public BoardEntity updateBoard(BoardEntity board) {
        if (!existsByBoardId(board.getId())) {
            throw new EntityNotFoundException("Board not found with id: " + board.getId());
        }
        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        BoardEntity board = getBoardById(id);
        log.info("Deleted board with id {}", board.getId());
        boardRepository.delete(board);
    }
}