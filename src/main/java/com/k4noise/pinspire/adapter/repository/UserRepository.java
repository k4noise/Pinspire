package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { }
