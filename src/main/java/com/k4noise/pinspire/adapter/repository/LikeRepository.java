package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByUserId(Long userId);

    List<LikeEntity> findByPinId(Long pinId);
}
