package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByUserId(Long userId);

    List<CommentEntity> findByPinId(Long pinId);
}
