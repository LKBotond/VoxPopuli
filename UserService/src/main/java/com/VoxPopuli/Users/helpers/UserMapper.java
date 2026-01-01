package com.VoxPopuli.Users.helpers;

import org.springframework.stereotype.Component;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.LoginResponse;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.dto.UserDto;

@Component
public class UserMapper {

    // From User
    public LoginResponse toLoginResponse(User user) {
        return LoginResponse.builder()
                .userId(user.getUserID())
                .alias(user.getAlias())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserID())
                .alias(user.getAlias())
                .passHash(user.getPassHash())
                .build();
    }

    // ToUser
    public User RegistrationRequestToUser(RegistrationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .alias(request.getAlias())
                .passHash(request.getPassHash())
                .build();
    }
}