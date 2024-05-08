package com.k4noise.pinspire.adapter.repository;

import com.k4noise.pinspire.domain.PinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<PinEntity, Long> {

    List<PinEntity> findByUserId(Long userId);

    List<PinEntity> findByBoardId(Long boardId);
}
