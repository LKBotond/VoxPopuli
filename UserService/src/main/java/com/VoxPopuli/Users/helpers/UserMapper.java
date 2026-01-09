package com.VoxPopuli.Users.helpers;

import org.springframework.stereotype.Component;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.PassRequest;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.dto.UserData;

@Component
public class UserMapper {

    // From User
    public UserData toUserData(User user) {
        return UserData.builder()
                .userId(user.getUserID())
                .alias(user.getAlias())
                .build();
    }

    // ToUser
    public User registrationRequestToUser(RegistrationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .alias(request.getAlias())
                .passHash(request.getPassHash())
                .build();
    }

    // only the pass hash
    public PassRequest toPassHash(User user) {
        return PassRequest.builder()
                .passHash(user.getPassHash())
                .build();
    }
}