package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByUserId(Long userId);
}
