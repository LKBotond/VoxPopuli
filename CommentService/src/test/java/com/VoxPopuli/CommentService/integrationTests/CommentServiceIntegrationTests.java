package com.VoxPopuli.CommentService.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.CommentService.dto.CommentResponse;
import com.VoxPopuli.CommentService.services.CommentService;
import com.VoxPopuli.CommentService.utils.ClassIntegrityTests;
import com.VoxPopuli.CommentService.utils.TestDataUtils;

@SpringBootTest
@Transactional
public class CommentServiceIntegrationTests {

    @Autowired
    private CommentService serviceTest;

    @Test
    public void commentCreationTest() {
        CommentResponse response = serviceTest.registerComment(TestDataUtils.createTestCommentRequest());
        ClassIntegrityTests.testObjectIntegrity(response);
    }

    @Test
    public void commentEditTest() {
        CommentResponse response = serviceTest.registerComment(TestDataUtils.createTestCommentRequest());
        CommentResponse edited = serviceTest.registerCommentEdit(
                TestDataUtils.createCommentEditRequest(response.getCommentId()));
        ClassIntegrityTests.testObjectIntegrity(edited);
        assertNotEquals(response, edited);
    }

    @Test
    public void commentdeletionTest() {
        CommentResponse response = serviceTest.registerComment(TestDataUtils.createTestCommentRequest());
        CommentResponse deleted = serviceTest.registerCommentDeletion(response.getCommentId());
        assertNull(deleted.getUserId());
        assertNull(deleted.getContent());
        assertNotEquals(response.getUpdatedAt(), deleted.getUpdatedAt());
    }

    @Test
    public void pageCommentsLoad() {
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
