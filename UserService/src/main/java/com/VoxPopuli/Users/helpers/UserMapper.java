package com.VoxPopuli.Users.helpers;

import org.springframework.stereotype.Component;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.usercontracts.HashedPass;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;
import com.VoxPopuli.usercontracts.UserData;

@Component
public class UserMapper {

    // From User
    public UserData toUserData(User user) {
        return UserData.builder()
                .userId(user.getUserID().toString())
                .alias(user.getAlias())
                .passHash(user.getPassHash())
                .build();
    }

    // ToUser
    public User registrationRequestToUser(HashedRegistrationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .alias(request.getAlias())
                .passHash(request.getPassHash())
                .build();
    }

    // only the pass hash
    public HashedPass toPassHash(User user) {
        return HashedPass.builder()
                .passHash(user.getPassHash())
                .build();
    }
}