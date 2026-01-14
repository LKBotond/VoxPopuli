package com.VoxPopuli.AuthenticationService.unitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.VoxPopuli.AuthenticationService.services.Argon2PassHashingService;

@SpringBootTest
public class PasswordHashTests {

    private final char[] legitPass = "password".toCharArray();
    private final char[] susPass = "susPass".toCharArray();

    @Autowired
    private Argon2PassHashingService passHashingService;

    @Test
    void TestPasswordHashing() {
        char[] localCopy = Arrays.copyOf(legitPass, legitPass.length);
        assertNotNull(localCopy);
        String hashed = passHashingService.hashWithArgon2(localCopy);

        assertNotNull(hashed);
        assertNotEquals(localCopy, hashed);
    }

    @Test
    void TestPasswordVerification() {
        char[] localCopy = Arrays.copyOf(legitPass, legitPass.length);
        char[] localCopy2 = Arrays.copyOf(legitPass, legitPass.length);
        char[] wrongCopy = Arrays.copyOf(susPass, susPass.length);

        String hashed = passHashingService.hashWithArgon2(localCopy);

        assertTrue(passHashingService.verifyPass(localCopy2, hashed));
        assertFalse(passHashingService.verifyPass(wrongCopy, hashed));
    }

    @Test
    void TestPasswordIsNulledAfterOperation() {
        char[] localCopy = Arrays.copyOf(legitPass, legitPass.length);

        passHashingService.hashWithArgon2(localCopy);
        assertNotEquals(localCopy, legitPass);
    }

}
