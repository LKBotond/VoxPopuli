//Chiacona in C Major for Violin and Continuo
package com.VoxPopuli.Users.integrationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.AuthenticationRequest;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.exceptions.AliasTakenException;
import com.VoxPopuli.Users.exceptions.EmailTakenException;
import com.VoxPopuli.Users.exceptions.UserNotFoundException;
import com.VoxPopuli.Users.services.UserService;
import com.VoxPopuli.Users.utils.ClassIntegrityTests;
import com.VoxPopuli.Users.utils.TestDataUtils;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testSuccesfulRegistration() {
        RegistrationRequest registrationRequest = TestDataUtils.createTestRegistrationRequest();
        User user = userService.createUser(registrationRequest);
        ClassIntegrityTests.testObjectIntegrity(user);
    }

    @Test
    public void testDuplicateEmailDetection() {
        RegistrationRequest registrationRequest = TestDataUtils.createTestRegistrationRequest();
        userService.createUser(registrationRequest);
        registrationRequest.setAlias("duplicate");
        assertThrows(EmailTakenException.class, () -> userService.createUser(registrationRequest));
    }

    @Test
    public void testDuplicateAliasDetection() {
        RegistrationRequest registrationRequest = TestDataUtils.createTestRegistrationRequest();
        userService.createUser(registrationRequest);
        registrationRequest.setEmail("duplicate@duplicate.com");
        assertThrows(AliasTakenException.class, () -> userService.createUser(registrationRequest));
    }

    @Test
    public void testSuccesfulLogin() {
        RegistrationRequest registrationRequest = TestDataUtils.createTestRegistrationRequest();
        userService.createUser(registrationRequest);
        AuthenticationRequest authenticationRequest = TestDataUtils.createAuthenticationRequest();
        User result = userService.loginByEmail(authenticationRequest);
        assertNotNull(result);
    }

    @Test
    public void testUnSuccesfulLogin() {
        RegistrationRequest registrationRequest = TestDataUtils.createTestRegistrationRequest();
        userService.createUser(registrationRequest);
        AuthenticationRequest authenticationRequest = TestDataUtils.createAuthenticationRequest();
        authenticationRequest.setEmail("null@null.null");
        assertThrows(UserNotFoundException.class, () -> userService.loginByEmail(authenticationRequest));
    }
}
