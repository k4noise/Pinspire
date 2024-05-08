package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.CommentEntity;
import com.k4noise.pinspire.adapter.repository.CommentRepository;
import com.k4noise.pinspire.domain.PinEntity;
import com.k4noise.pinspire.domain.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {
    CommentRepository commentRepository;
    UserService userService;
    PinService pinService;

    public boolean existsCommentById(Long id) {
        return commentRepository.existsById(id);
    }

    public CommentEntity getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    public List<CommentEntity> getCommentsByUser(Long userId) {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return commentRepository.findByUserId(userId);
    }

    public List<CommentEntity> getCommentsByPin(Long pinId) {
        if (!pinService.existsPinById(pinId)) {
            throw new EntityNotFoundException("Pin not found with id: " + pinId);
        }
        return commentRepository.findByPinId(pinId);
    }

    @Transactional
    public CommentEntity createComment(CommentEntity comment, Long userId, Long pinId) {
        UserEntity user = userService.getUserById(userId);
        PinEntity pin = pinService.getPinById(pinId);
        comment.setUser(user);
        comment.setPin(pin);
        return commentRepository.save(comment);
    }

    @Transactional
    public CommentEntity updateComment(CommentEntity comment) {
        if (!existsCommentById(comment.getId())) {
            throw new EntityNotFoundException("Comment not found with id: " + comment.getId());
        }
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        CommentEntity comment = getCommentById(id);
        commentRepository.delete(comment);
    }
}