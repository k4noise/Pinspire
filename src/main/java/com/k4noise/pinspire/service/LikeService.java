package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.LikeEntity;
import com.k4noise.pinspire.adapter.repository.LikeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LikeService {

    LikeRepository likeRepository;

    public List<LikeEntity> getAllLikes() {
        return likeRepository.findAll();
    }

    public Optional<LikeEntity> getLikeById(Long id) {
        return likeRepository.findById(id);
    }

    public List<LikeEntity> getLikesByUser(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    public List<LikeEntity> getLikesByPin(Long pinId) {
        return likeRepository.findByPinId(pinId);
    }

    public LikeEntity createLike(LikeEntity like) {
        return likeRepository.save(like);
    }

    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }
}