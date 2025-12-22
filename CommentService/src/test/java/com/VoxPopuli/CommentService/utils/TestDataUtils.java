package com.VoxPopuli.CommentService.utils;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.CommentService.dto.CommentEditRequest;
import com.VoxPopuli.CommentService.dto.CommentRequest;

public final class TestDataUtils {

    private TestDataUtils() {
    }

    public static Comment createTestComment() {
        return Comment.builder()
                .parentId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .sourceLinkHash("www.webpage.hash")
                .content("lorem ipsum")
                .lastUpdated(OffsetDateTime.now())
                .build();
    }

    public static CommentRequest createTestCommentRequest() {
        return CommentRequest.builder()
                .parentId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .sourceLinkHash("www.webpage.hash")
                .content("lorem ipsum")
                .updatedAt(OffsetDateTime.now())
                .build();
    }


    public static CommentEditRequest createCommentEditRequest(UUID commentId) {
        return CommentEditRequest.builder().commentId(commentId)
                .editedContent("editum editam editarem editatem edituram editim").build();
    }
}
