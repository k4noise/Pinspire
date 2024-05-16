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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponse(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return userMapper.entityToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserEntity getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
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
    public UserResponseDto updateUser(UserDetails userDetails, UserRequestDto userDto) throws EntityExistsException{
        UserEntity user = getUserEntityByUsername(userDetails.getUsername());
        if (!Objects.equals(user.getUsername(), userDto.username()) && userRepository.existsUserByUsername(userDto.username())) {
            throw new EntityExistsException("User with username " + userDto.username() + " already exists");
        }
        if (!Objects.equals(user.getEmail(), userDto.email()) && userRepository.existsUserByEmail(userDto.email())) {
            throw new EntityExistsException("User with email " + userDto.email() + " already exists");
        }
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        if (!userDto.password().isBlank()) {
            user.setPassword(encoder.encode(userDto.password()));
        }
        user.setDisplayName(Optional.ofNullable(userDto.displayName()).orElse(user.getDisplayName()));
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(UserDetails userDetails) {
        UserEntity user = getUserEntityByUsername(userDetails.getUsername());
        userRepository.delete(user);
        log.info("Deleted user with id {}", user.getId());
    }
}