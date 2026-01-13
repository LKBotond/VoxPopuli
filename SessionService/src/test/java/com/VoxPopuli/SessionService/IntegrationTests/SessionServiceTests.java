package com.VoxPopuli.SessionService.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;
import com.VoxPopuli.SessionService.domain.SessionDomain;
import com.VoxPopuli.SessionService.dtos.SessionToken;
import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.exceptions.InvalidSessionException;
import com.VoxPopuli.SessionService.repositories.RedisRepo;
import com.VoxPopuli.SessionService.services.SessionService;
import com.VoxPopuli.SessionService.utils.TestDataUtils;
import com.redis.testcontainers.RedisContainer;

@Testcontainers
@SpringBootTest
public class SessionServiceTests {

    @Container
    @ServiceConnection
    @SuppressWarnings("resource")
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer(
            DockerImageName.parse("redis:7-alpine")).withExposedPorts(6379);

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @Test
    void givenRedisContainerConfiguredWithDynamicProperties_whenCheckingRunningStatus_thenStatusIsRunning() {
        assertTrue(REDIS_CONTAINER.isRunning());
    }

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RedisRepo sessionRepo;

    @Test
    public void testSessionCreation() {
        SessionToken response = sessionService.createSession(TestDataUtils.createSessionCreationRequest());
        assertNotNull(response);
    }

    @Test
    public void testSessionValidation() {
        SessionToken saved = sessionService.createSession(TestDataUtils.createSessionCreationRequest());
        SessionToken request = new SessionToken(saved.getSessionId(), saved.getAlias());
        SessionCreationRequest response = sessionService.validateSession(request);
        assertNotNull(response.getAlias());
        assertNotNull(response.getUserId());
    }

    @Test
    public void testTimeout() {

        SessionDomain token = TestDataUtils.createSessionToken(2);
        sessionService.saveToken(token);
        Optional<SessionDomain> stored = sessionRepo.findById(token.getSessionId());

        assertTrue(stored.isPresent());
        await().atMost(5, SECONDS).untilAsserted(() -> {
            assertTrue(sessionRepo.findById(token.getSessionId()).isEmpty());
        });
    }

    @Test
    public void testSessionDeletion() {
        SessionToken response = sessionService.createSession(TestDataUtils.createSessionCreationRequest());
        sessionService.endSession(response.getSessionId());

        SessionToken validationRequest = new SessionToken(response.getSessionId(), response.getAlias());
        assertThrows(
                InvalidSessionException.class,
                () -> sessionService.validateSession(validationRequest));
    }

    @AfterEach
    void cleanup() {
        sessionRepo.deleteAll();
    }
}
