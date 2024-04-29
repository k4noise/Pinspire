package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.BoardEntity;
import com.k4noise.pinspire.adapter.repository.BoardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;

    public List<BoardEntity> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<BoardEntity> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public List<BoardEntity> getBoardsByUser(Long userId) {
        return boardRepository.findByUserId(userId);
    }

    public BoardEntity createBoard(BoardEntity board) {
        return boardRepository.save(board);
    }

    public BoardEntity updateBoard(BoardEntity board) {
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}