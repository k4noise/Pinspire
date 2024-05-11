package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.dto.request.PinRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.PinResponseDto;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
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

    public boolean existsPinById(Long id) {
        return pinRepository.existsById(id);
    }

    public PinResponseDto getPinById(Long id) {
        return pinMapper.entityToResponse(getPinEntityById(id));
    }

    public PinEntity getPinEntityById(Long id) throws EntityNotFoundException {
        return pinRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Pin not found with id: " + id));
    }

    public List<PinResponseDto> getPinsByUser(Long userId) throws EntityNotFoundException {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return pinMapper.entitiesToResponse(pinRepository.findByUserId(userId));
    }

    public List<PinResponseDto> getPinsByBoard(Long boardId) throws EntityNotFoundException{
        if (!boardService.existsByBoardId(boardId)) {
            throw new EntityNotFoundException("Board not found with id: " + boardId);
        }
        return pinMapper.entitiesToResponse(pinRepository.findByBoardId(boardId));
    }

    @Transactional
    public PinResponseDto createPin(Principal principal, PinRequestDto pinDto) throws EntityNotFoundException {
        UserEntity user = userService.getUserEntityById(pinDto.userId());
        BoardEntity board = boardService.getBoardEntityById(pinDto.boardId());

        if (!Objects.equals(principal.getName(), user.getUsername())) {
            throw new AccessDeniedException("Action with another user is prohibited");
        }

        if (!user.getBoards().contains(board)) {
            throw new AccessDeniedException("Action with another user board is prohibited");
        }

        PinEntity pin = new PinEntity();
        pin.setTitle(pinDto.title());
        pin.setDescription(pinDto.description());
        pin.setImageUrl(pinDto.imageUrl());
        pin.setUploadedAt(LocalDateTime.now());
        pin.setUser(user);
        pin.setBoard(board);

        PinEntity savedPin = pinRepository.save(pin);
        log.info("Created pin with id {}", pin.getId());
        return pinMapper.entityToResponse(savedPin);
    }

    @Transactional
    public PinResponseDto updatePin(Principal principal, Long id, PinRequestDto pinDto) throws EntityNotFoundException, AccessDeniedException {
        PinEntity pin = getPinEntityById(id);
        if (!Objects.equals(principal.getName(), pin.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }
        if (!existsPinById(pin.getId())) {
            throw new EntityNotFoundException("Pin not found with id " + pin.getId());
        }
        pin.setTitle(pinDto.title());
        pin.setDescription(pinDto.description());
        pin.setImageUrl(pinDto.imageUrl());
        return pinMapper.entityToResponse(pinRepository.save(pin));
    }

    @Transactional
    public void deletePin(Principal principal, Long id) throws EntityNotFoundException, AccessDeniedException {
        PinEntity pin = getPinEntityById(id);
        if (!Objects.equals(principal.getName(), pin.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user pin is prohibited");
        }
        pinRepository.delete(pin);
        log.info("Deleted pin with id {}", pin.getId());
    }
}