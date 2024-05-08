package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.PinEntity;
import com.k4noise.pinspire.adapter.repository.PinRepository;
import com.k4noise.pinspire.domain.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PinService {
    PinRepository pinRepository;
    UserService userService;
    BoardService boardService;

    public boolean existsPinById(Long id) {
        return pinRepository.existsById(id);
    }

    public List<PinEntity> getAllPins() {
        return pinRepository.findAll();
    }

    public PinEntity getPinById(Long id) {
        return pinRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Pin not found with id: " + id));
    }

    public List<PinEntity> getPinsByUser(Long userId) {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return pinRepository.findByUserId(userId);
    }

    public List<PinEntity> getPinsByBoard(Long boardId) {
        if (!boardService.existsByBoardId(boardId)) {
            throw new EntityNotFoundException("Board not found with id: " + boardId);
        }
        return pinRepository.findByBoardId(boardId);
    }

    @Transactional
    public PinEntity createPin(PinEntity pin, Long userId) {
        UserEntity user = userService.getUserById(userId);
        pin.setUser(user);

        log.info("Created pin with id {}", pin.getId());
        return pinRepository.save(pin);
    }

    @Transactional
    public PinEntity updatePin(PinEntity pin) {
        if (pinRepository.existsById(pin.getId())) {
            throw new EntityNotFoundException("Pin not found with id " + pin.getId());
        }
        return pinRepository.save(pin);
    }

    @Transactional
    public void deletePin(Long id) {
        PinEntity pin = getPinById(id);
        log.info("Deleted pin with id {}", pin.getId());
        pinRepository.delete(pin);
    }
}