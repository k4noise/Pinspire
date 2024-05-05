package com.k4noise.pinspire.service;

import com.k4noise.pinspire.domain.UserEntity;
import com.k4noise.pinspire.adapter.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
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
public class UserService {
    UserRepository userRepository;

    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            throw new EntityExistsException("User with username " + user.getUsername() + " already exists");
        }
        log.info("Created user with id {} and username {}", user.getId(), user.getUsername());
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity updateUser(UserEntity user) {
        if (!existsUserById(user.getId())) {
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = getUserById(id);
        log.info("Deleted user with id {}", user.getId());
        userRepository.delete(user);
    }
}