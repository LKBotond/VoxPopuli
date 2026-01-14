package com.VoxPopuli.AuthenticationService.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.VoxPopuli.AuthenticationService.clients.SesssionClient;
import com.VoxPopuli.AuthenticationService.clients.UserClient;
import com.VoxPopuli.AuthenticationService.exceptions.InvalidPassException;
import com.VoxPopuli.AuthenticationService.mappers.SessionMapper;
import com.VoxPopuli.AuthenticationService.sagaSpecifics.SagaStep;
import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;
import com.VoxPopuli.usercontracts.UserData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Argon2PassHashingService passHashingService;
    private final UserClient userClient;
    private final SesssionClient sessionClient;

    public SessionToken registerUser(RegistrationRequest request) {
        List<SagaStep> steps = new ArrayList<>();
        try {

            UserData data = userClient.register(buildHashedRegistrationRequest(request));
            steps.add(buildStep("registration", () -> {
                userClient.deleteUser(data.getUserId());
            }));

            SessionToken token = sessionClient.createSession(SessionMapper.fromUserData(data));
            steps.add(buildStep("tokenGeneration", () -> {
                sessionClient.endSession(token.getSessionId());
            }));
            return token;

        } catch (Exception e) {
            rollbackSaga(steps);
            throw e;
        }
    }

    public SessionToken loginUser(LoginRequest request) {
        UserData data = userClient.getUserByEmail(request.getEmail());
        if (!passHashingService.verifyPass(request.getPass(), data.getPassHash())) {
            throw new InvalidPassException("Passes do not match for email: " + request.getEmail());
        }
        return sessionClient.createSession(SessionMapper.fromUserData(data));
    }

    private String hashPass(char[] pass) {
        return passHashingService.hashWithArgon2(pass);
    }

    private HashedRegistrationRequest buildHashedRegistrationRequest(RegistrationRequest request) {
        String hashedPass = hashPass(request.getPassArray());
        return HashedRegistrationRequest.builder()
                .alias(request.getAlias())
                .email(request.getEmail())
                .passHash(hashedPass).build();
    }

    private void rollbackSaga(List<SagaStep> steps) {
        for (int i = steps.size() - 1; i >= 0; i--) {
            steps.get(i).rollback();
        }
    }

    private SagaStep buildStep(String name, Runnable undo) {
        return SagaStep.builder().name(name).rollback(undo).build();
    }
}
