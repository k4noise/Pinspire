package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.dto.request.NotificationRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.LikeResponseDto;
import com.k4noise.pinspire.domain.LikeEntity;
import com.k4noise.pinspire.adapter.repository.LikeRepository;
import com.k4noise.pinspire.domain.PinEntity;
import com.k4noise.pinspire.domain.UserEntity;
import com.k4noise.pinspire.service.mapper.LikeMapper;
import com.k4noise.pinspire.service.notification.NotificationPublisherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LikeService {
    LikeRepository likeRepository;
    UserService userService;
    PinService pinService;
    NotificationPublisherService notificationService;
    LikeMapper likeMapper;

    @Transactional(readOnly = true)
    public LikeResponseDto getLikeById(Long id) {
        return likeMapper.entityToResponse(getLikeEntityById(id));
    }

    @Transactional(readOnly = true)
    public LikeEntity getLikeEntityById(Long id) throws EntityNotFoundException {
        return likeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Like not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<LikeResponseDto> getLikesByUser(Long userId) throws EntityNotFoundException {
        if (!userService.existsUserById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return likeMapper.entitiesToResponse(likeRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<LikeResponseDto> getLikesByPin(Long pinId) throws EntityNotFoundException {
        if (!pinService.existsPinById(pinId)) {
            throw new EntityNotFoundException("Pin not found with id: " + pinId);
        }
        return likeMapper.entitiesToResponse(likeRepository.findByPinId(pinId));
    }

    @Transactional
    public void createLike(UserDetails userDetails, Long pinId) throws EntityNotFoundException  {
        PinEntity pin = pinService.getPinEntityById(pinId);
        UserEntity user = userService.getUserEntityByUsername(userDetails.getUsername());

        LikeEntity like = LikeEntity.create(user, pin);
        if (!Objects.equals(user.getUsername(), pin.getUser().getUsername())) {
            NotificationRequestDto likeNotification = new NotificationRequestDto(pin.getUser().getUsername(), user.getUsername(), pin.getTitle(), pinId);
            notificationService.sendLikeNotification(likeNotification);
        }
        likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(UserDetails userDetails, Long id) throws EntityNotFoundException, AccessDeniedException  {
        LikeEntity like = getLikeEntityById(id);
        if (!Objects.equals(userDetails.getUsername(), like.getUser().getUsername())) {
            throw new AccessDeniedException("Action with another user like is prohibited");
        }
        likeRepository.delete(like);
    }
}