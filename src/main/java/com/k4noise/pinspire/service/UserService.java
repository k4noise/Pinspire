package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.dto.request.UserRequestDto;
import com.k4noise.pinspire.adapter.web.dto.response.UserResponseDto;
import com.k4noise.pinspire.domain.UserEntity;
import com.k4noise.pinspire.adapter.repository.UserRepository;
import com.k4noise.pinspire.service.mapper.UserMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder encoder;

    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponse(userRepository.findAll());
    }

    public UserResponseDto getUserById(Long id) {
        return userMapper.entityToResponse(getUserEntityById(id));
    }

    public UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public UserResponseDto registerUser(UserRequestDto userDto) throws EntityExistsException {
        if (userRepository.existsUserByUsername(userDto.username())) {
            throw new EntityExistsException("User with username " + userDto.username() + " already exists");
        }
        if (userRepository.existsUserByEmail(userDto.email())) {
            throw new EntityExistsException("User with email " + userDto.email() + " already exists");
        }

        String encodedPassword = encoder.encode(userDto.password());
        UserEntity newUser = UserEntity.create(userDto.username(), userDto.email(), encodedPassword);
        UserEntity savedUser = userRepository.save(newUser);
        log.info("Created user with id {} and username {}", savedUser.getId(), savedUser.getUsername());
        return userMapper.entityToResponse(savedUser);
    }

    @Transactional
    public UserResponseDto updateUser(Principal principal, Long id, UserRequestDto userDto) throws EntityNotFoundException, AccessDeniedException {
        UserEntity user = getUserEntityById(id);
        if (!Objects.equals(user.getUsername(), principal.getName())) {
            throw new AccessDeniedException("Action with another user is prohibited");
        }
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(encoder.encode(userDto.password()));
        user.setDisplayName(Optional.ofNullable(userDto.displayName()).orElse(user.getDisplayName()));
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Principal principal, Long id) throws EntityNotFoundException, AccessDeniedException {
        UserEntity user = getUserEntityById(id);
        if (!Objects.equals(user.getUsername(), principal.getName())) {
            throw new AccessDeniedException("Action with another user is prohibited");
        }
        userRepository.delete(user);
        log.info("Deleted user with id {}", user.getId());
    }
}