package com.VoxPopuli.AuthenticationService.mappers;

import com.VoxPopuli.sessioncontracts.InternalUserData;
import com.VoxPopuli.usercontracts.UserData;

public final class SessionMapper {

    private SessionMapper() {
    };

    public static final InternalUserData fromUserData(UserData userData) {
        return InternalUserData.builder().alias(userData.getAlias()).userId(userData.getUserId()).build();
    }
}
