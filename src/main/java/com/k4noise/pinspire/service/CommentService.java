package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.CommentEntity;
import com.k4noise.pinspire.adapter.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {
    CommentRepository commentRepository;

    public List<CommentEntity> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<CommentEntity> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<CommentEntity> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public List<CommentEntity> getCommentsByPin(Long pinId) {
        return commentRepository.findByPinId(pinId);
    }

    public CommentEntity createComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public CommentEntity updateComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}