package com.VoxPopuli.AuthenticationService.mappers;

import com.VoxPopuli.sessioncontracts.SessionCreationRequest;
import com.VoxPopuli.usercontracts.UserData;

public final class SessionMapper {

    private SessionMapper() {
    };

    public static final SessionCreationRequest fromUserData(UserData userData) {
        return SessionCreationRequest.builder().alias(userData.getAlias()).userId(userData.getUserId()).build();
    }
}
