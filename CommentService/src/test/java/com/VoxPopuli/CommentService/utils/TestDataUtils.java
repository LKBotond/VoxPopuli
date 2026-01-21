package com.VoxPopuli.CommentService.utils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.commentcontracts.CommentEditRequest;
import com.VoxPopuli.commentcontracts.CommentRequest;
import com.VoxPopuli.filtercontracts.CensorResponse;

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
                .parentId(UUID.randomUUID().toString())
                .sourceLinkHash("www.webpage.hash")
                .content("lorem ipsum")
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    public static CommentEditRequest createCommentEditRequest(UUID commentId) {
        return CommentEditRequest.builder().commentId(commentId.toString())
                .editedContent("editum editam editarem editatem edituram editim").build();
    }

    public static CensorResponse createUnflagged() {
        List<String> empty = new ArrayList<>();
        return CensorResponse.builder().caughtWords(empty).flagged(false).build();
    }

    public static UUID getUUID() {
        return UUID.randomUUID();
    }
}
