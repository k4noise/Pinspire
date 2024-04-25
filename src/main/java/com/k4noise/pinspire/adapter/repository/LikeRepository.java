package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> { }
