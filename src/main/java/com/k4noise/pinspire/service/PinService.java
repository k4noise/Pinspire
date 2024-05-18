package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.dto.request.PinRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.PinResponseDto;
import com.k4noise.pinspire.adapter.web.errors.FileStorageException;
import com.k4noise.pinspire.common.metrics.counter.CounterMetric;
import com.k4noise.pinspire.domain.BoardEntity;
import com.k4noise.pinspire.domain.PinEntity;
import com.k4noise.pinspire.adapter.repository.PinRepository;
import com.k4noise.pinspire.domain.UserEntity;
import com.k4noise.pinspire.service.mapper.PinMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PinService {
    PinRepository pinRepository;
    PinMapper pinMapper;
    UserService userService;
    BoardService boardService;
    FileStorageService fileService;

    @Transactional(readOnly = true)
    public boolean existsPinById(Long id) {
        return pinRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public PinResponseDto getPinById(Long id) {
        return pinMapper.entityToResponse(getPinEntityById(id));
    }

    @Transactional(readOnly = true)
    public PinEntity getPinEntityById(Long id) throws EntityNotFoundException {
        return pinRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Pin not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PinResponseDto> getPinsByUser(Long userId) throws EntityNotFoundException {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return pinMapper.entitiesToResponse(pinRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<PinResponseDto> getPinsByBoard(Long boardId) throws EntityNotFoundException{
        if (!boardService.existsByBoardId(boardId)) {
            throw new EntityNotFoundException("Board not found with id: " + boardId);
        }
        return pinMapper.entitiesToResponse(pinRepository.findByBoardId(boardId));
    }

    @Transactional
    @CounterMetric
    public PinResponseDto createPin(UserDetails userDetails, Long boardId, PinRequestDto pinDto) throws AccessDeniedException {
        BoardEntity board = boardService.getBoardEntityById(boardId);
        UserEntity user = userService.getUserEntityByUsername(userDetails.getUsername());

        if (!user.getBoards().contains(board)) {
            throw new AccessDeniedException("Action with another userDetails board is prohibited");
        }

        if (!fileService.isUrlValid(pinDto.imageUrl())) {
            throw new FileStorageException("File URL is not valid: " + pinDto.imageUrl(), HttpStatus.BAD_REQUEST);
        }

        if (!fileService.urlExists(pinDto.imageUrl())) {
            throw new FileStorageException("File not exists with given URL: " + pinDto.imageUrl(), HttpStatus.NOT_FOUND);
        }

        PinEntity pin = pinRepository.save(PinEntity.create(pinDto, user, board));
        log.info("Created pin with id {}", pin.getId());
        return pinMapper.entityToResponse(pin);
    }

    @Transactional
    public PinResponseDto updatePin(UserDetails userDetails, Long id, PinRequestDto pinDto) throws EntityNotFoundException, AccessDeniedException {
        PinEntity pin = getPinEntityById(id);
        if (!Objects.equals(userDetails.getUsername(), pin.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another userDetails pin is prohibited");
        }
        pin.setTitle(pinDto.title());
        pin.setDescription(pinDto.description());
        pin.setImageUrl(pinDto.imageUrl());
        return pinMapper.entityToResponse(pinRepository.save(pin));
    }

    @Transactional
    public void deletePin(UserDetails userDetails, Long id) throws EntityNotFoundException, AccessDeniedException {
        PinEntity pin = getPinEntityById(id);
        if (!Objects.equals(userDetails.getUsername(), pin.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }
        pinRepository.delete(pin);
        log.info("Deleted pin with id {}", pin.getId());
    }
}