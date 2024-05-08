package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.LikeEntity;
import com.k4noise.pinspire.adapter.repository.LikeRepository;
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
public class LikeService {
    LikeRepository likeRepository;
    UserService userService;
    PinService pinService;

    public LikeEntity getLikeById(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Like not found with id: " + id));
    }

    public List<LikeEntity> getLikesByUser(Long userId) {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return likeRepository.findByUserId(userId);
    }

    public List<LikeEntity> getLikesByPin(Long pinId) {
        if (!pinService.existsPinById(pinId)) {
            throw new EntityNotFoundException("Pin not found with id: " + pinId);
        }
        return likeRepository.findByPinId(pinId);
    }

    @Transactional
    public LikeEntity createLike(LikeEntity like, Long userId, Long pinId) {
        UserEntity user = userService.getUserById(userId);
        PinEntity pin = pinService.getPinById(pinId);
        like.setUser(user);
        like.setPin(pin);
        return likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(Long id) {
        LikeEntity like = getLikeById(id);
        likeRepository.delete(like);
    }
}