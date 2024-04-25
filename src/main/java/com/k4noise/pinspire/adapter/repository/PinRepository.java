package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.PinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends JpaRepository<PinEntity, Long> { }
