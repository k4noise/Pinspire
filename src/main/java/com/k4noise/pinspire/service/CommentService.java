package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.dto.request.CommentRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.CommentResponseDto;
import com.k4noise.pinspire.domain.CommentEntity;
import com.k4noise.pinspire.adapter.repository.CommentRepository;
import com.k4noise.pinspire.domain.PinEntity;
import com.k4noise.pinspire.domain.UserEntity;
import com.k4noise.pinspire.service.mapper.CommentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {
    CommentRepository commentRepository;
    UserService userService;
    PinService pinService;
    CommentMapper commentMapper;

    public boolean existsCommentById(Long id) {
        return commentRepository.existsById(id);
    }

    public CommentResponseDto getCommentById(Long id) {
        return commentMapper.entityToResponse(getCommentEntityById(id));
    }

    public CommentEntity getCommentEntityById(Long id) throws EntityNotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    public List<CommentResponseDto> getCommentsByUser(Long userId) throws EntityNotFoundException {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return commentMapper.entitiesToResponse(commentRepository.findByUserId(userId));
    }

    public List<CommentResponseDto> getCommentsByPin(Long pinId) throws EntityNotFoundException {
        if (!pinService.existsPinById(pinId)) {
            throw new EntityNotFoundException("Pin not found with id: " + pinId);
        }
        return commentMapper.entitiesToResponse(commentRepository.findByPinId(pinId));
    }

    @Transactional
    public CommentResponseDto createComment(UserDetails userDetails, Long pinId, CommentRequestDto commentDto) throws EntityNotFoundException  {
        PinEntity pin = pinService.getPinEntityById(pinId);
        UserEntity user = userService.getUserEntityByUsername(userDetails.getUsername());

        CommentEntity comment = new CommentEntity();
        comment.setText(commentDto.text());
        comment.setUser(user);
        comment.setPin(pin);
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.entityToResponse(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponseDto updateComment(UserDetails userDetails, Long id, CommentRequestDto commentDto) throws EntityNotFoundException, AccessDeniedException {
        if (!existsCommentById(id)) {
            throw new EntityNotFoundException("Comment not found with id: " + id);
        }
        CommentEntity comment = getCommentEntityById(id);

        if (!Objects.equals(userDetails.getUsername(), comment.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }

        comment.setText(commentDto.text());
        return commentMapper.entityToResponse(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(UserDetails userDetails, Long id) throws EntityNotFoundException, AccessDeniedException {
        CommentEntity comment = getCommentEntityById(id);
        if (!Objects.equals(userDetails.getUsername(), comment.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }
        commentRepository.delete(comment);
    }
}