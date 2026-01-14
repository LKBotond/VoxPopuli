package com.VoxPopuli.AuthenticationService.integrationTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.VoxPopuli.AuthenticationService.services.AuthenticationService;


@SpringBootTest
public class AccessServiceTest {

    private final char[] legitPass = "password".toCharArray();
    private final char[] susPass = "susPass".toCharArray();

    @Autowired
    AuthenticationService authService;

    

}
