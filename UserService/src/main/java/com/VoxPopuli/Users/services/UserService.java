package com.VoxPopuli.Users.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.AuthenticationRequest;
import com.VoxPopuli.Users.dto.DeletionRequest;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.exceptions.AliasTakenException;
import com.VoxPopuli.Users.exceptions.EmailTakenException;
import com.VoxPopuli.Users.exceptions.UserNotFoundException;
import com.VoxPopuli.Users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(RegistrationRequest registrationRequest) {
        try {
            if (checkForAlias(registrationRequest.getAlias())) {
                throw new AliasTakenException("Alias already taken: " + registrationRequest.getAlias());
            }
            if (checkForEmail(registrationRequest.getEmail())) {
                throw new EmailTakenException("Email already taken: " + registrationRequest.getEmail());
            }
            return userRepository.save(registrationRequest.mapToUser());
        } catch (DataIntegrityViolationException e) {

            throw new RuntimeException("User creation failed due to duplicate entry", e);
        }
    }

    public User loginByEmail(AuthenticationRequest authenticationRequest) {
        return userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("No user with Such email" + authenticationRequest.getEmail()));
    }

    public void deleteUser(DeletionRequest deletionRequest) {
        userRepository.deleteById(deletionRequest.getUserId());
    }

    private boolean checkForAlias(String alias) {
        return userRepository.findByAlias(alias).isPresent();

    }

    private boolean checkForEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
