package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.PinEntity;
import com.k4noise.pinspire.adapter.repository.PinRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PinService {
    PinRepository pinRepository;

    public List<PinEntity> getAllPins() {
        return pinRepository.findAll();
    }

    public Optional<PinEntity> getPinById(Long id) {
        return pinRepository.findById(id);
    }

    public List<PinEntity> getPinsByUser(Long userId) {
        return pinRepository.findByUserId(userId);
    }

    public List<PinEntity> getPinsByBoard(Long boardId) {
        return pinRepository.findByBoardId(boardId);
    }

    public PinEntity createPin(PinEntity pin) {
        return pinRepository.save(pin);
    }

    public PinEntity updatePin(PinEntity pin) {
        return pinRepository.save(pin);
    }

    public void deletePin(Long id) {
        pinRepository.deleteById(id);
    }
}