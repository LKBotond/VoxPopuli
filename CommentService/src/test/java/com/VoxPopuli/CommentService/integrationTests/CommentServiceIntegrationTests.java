package com.VoxPopuli.CommentService.integrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.VoxPopuli.CommentService.clients.FilterClient;
import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.CommentService.services.CommentService;
import com.VoxPopuli.CommentService.utils.ClassIntegrityTests;
import com.VoxPopuli.CommentService.utils.TestDataUtils;
import com.VoxPopuli.commentcontracts.CommentRequest;
import com.VoxPopuli.commentcontracts.CommentResponse;
import com.VoxPopuli.filtercontracts.CensorRequest;
import com.VoxPopuli.filtercontracts.CensorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Testcontainers
@Transactional
@ActiveProfiles("test")
public class CommentServiceIntegrationTests {

    @MockitoBean
    FilterClient client;

    @Autowired
    private CommentService serviceTest;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void commentCreationTest() {
        CensorResponse cResponse = TestDataUtils.createUnflagged();
        when(client.checkRequest(any(CensorRequest.class))).thenReturn(TestDataUtils.createUnflagged());

        CommentRequest request = TestDataUtils.createTestCommentRequest();
        CommentResponse response = serviceTest.registerComment(request);
        assertThat(response).hasNoNullFieldsOrProperties();

    }

    @Test
    public void commentEditTest() {
        CensorResponse cResponse = TestDataUtils.createUnflagged();
        when(client.checkRequest(any(CensorRequest.class))).thenReturn(cResponse);
        CommentResponse response = serviceTest.registerComment(TestDataUtils.createTestCommentRequest());
        CommentResponse edited = serviceTest.registerCommentEdit(
                TestDataUtils.createCommentEditRequest(UUID.fromString(response.getCommentId())));
        ClassIntegrityTests.testObjectIntegrity(edited);
        assertNotEquals(response, edited);
    }

    @Test
    public void commentdeletionTest() {
        CensorResponse cResponse = TestDataUtils.createUnflagged();
        when(client.checkRequest(any(CensorRequest.class))).thenReturn(cResponse);

        CommentResponse response = serviceTest.registerComment(TestDataUtils.createTestCommentRequest());
        log.info("response UUID: " + response.getUserId());
        log.info("response parentUUID: " + response.getParentId());
        log.info("response commentUUID: " + response.getCommentId());
        log.info("response UUID: " + response.getCommentId());
        CommentResponse deleted = serviceTest.registerCommentDeletion(UUID.fromString(response.getCommentId()));
        assertNotEquals(response.getUpdatedAt(), deleted.getUpdatedAt());
        assertNotEquals(response.getContent(), deleted.getContent());
        assertNotEquals(response.getUserId(), deleted.getUserId());
    }

    @Test
    public void pageCommentsLoad() {
        CensorResponse cResponse = TestDataUtils.createUnflagged();
        when(client.checkRequest(any(CensorRequest.class))).thenReturn(cResponse);
        String sourceLinkHash = registerTenComments();
        List<CommentResponse> pageComments = serviceTest.getAllCommentsForSite(sourceLinkHash);
        assertEquals(pageComments.size(), 10);
    }

    /**
     * THis registers 10 comments for the same sourceLinkHash
     * 
     * @return sourceLinkHash
     */
    private String registerTenComments() {
        Comment comment = TestDataUtils.createTestComment();
        for (int i = 0; i < 10; i++) {
            serviceTest.registerComment(TestDataUtils.createTestCommentRequest());
        }
        return comment.getSourceLinkHash();

    }
}
