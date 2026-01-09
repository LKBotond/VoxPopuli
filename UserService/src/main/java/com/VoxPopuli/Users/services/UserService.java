package com.VoxPopuli.Users.services;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.exceptions.AliasTakenException;
import com.VoxPopuli.Users.exceptions.EmailTakenException;
import com.VoxPopuli.Users.exceptions.UserNotFoundException;
import com.VoxPopuli.Users.helpers.UserMapper;
import com.VoxPopuli.Users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(RegistrationRequest registrationRequest) {
        try {
            if (checkForAlias(registrationRequest.getAlias())) {
                throw new AliasTakenException("Alias already taken: " + registrationRequest.getAlias());
            }
            if (checkForEmail(registrationRequest.getEmail())) {
                throw new EmailTakenException("Email already taken: " + registrationRequest.getEmail());
            }
            return userRepository.save(userMapper.registrationRequestToUser(registrationRequest));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("User creation failed due to duplicate entry", e);
        }
    }

    public User loginByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with Such email" + email));
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user with Such id" + id));
    }

    public void changePass(UUID userId, String newPass) {
        userRepository.updatePassByUserId(userId, newPass);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    private boolean checkForAlias(String alias) {
        return userRepository.findByAlias(alias).isPresent();
    }

    private boolean checkForEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
