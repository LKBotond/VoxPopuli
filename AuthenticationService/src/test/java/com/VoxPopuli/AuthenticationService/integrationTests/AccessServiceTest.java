package com.VoxPopuli.AuthenticationService.integrationTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.VoxPopuli.AuthenticationService.dtos.HashedPass;
import com.VoxPopuli.AuthenticationService.dtos.PassHashRequest;
import com.VoxPopuli.AuthenticationService.dtos.PassValidationRequest;
import com.VoxPopuli.AuthenticationService.services.AuthenticationService;

@SpringBootTest
public class AccessServiceTest {

    private final char[] legitPass = "password".toCharArray();
    private final char[] susPass = "susPass".toCharArray();

    @Autowired
    AuthenticationService authService;

    @Test
    void testHashing() {
        char[] legitCopy = legitPass;
        HashedPass passHash = authService.hashPass(new PassHashRequest(legitCopy));
        assertNotNull(passHash);
        assertNotNull(passHash.getHashedPass());
        assertNotEquals(passHash.getHashedPass(), legitPass.toString());
    }

    @Test
    void testSuccesfulValidation() {
        char[] legitCopy = Arrays.copyOf(legitPass, legitPass.length);
        char[] legitCopy2 = Arrays.copyOf(legitPass, legitPass.length);
        HashedPass passHash = authService.hashPass(new PassHashRequest(legitCopy));
        assertTrue(authService.authenticatePassword(new PassValidationRequest(passHash.getHashedPass(), legitCopy2)));
    }

    @Test
    void testForFalsePassfRejection() {
        HashedPass passHash = authService.hashPass(new PassHashRequest(legitPass));
        assertFalse(authService.authenticatePassword(new PassValidationRequest(passHash.getHashedPass(), susPass)));
    }

}
