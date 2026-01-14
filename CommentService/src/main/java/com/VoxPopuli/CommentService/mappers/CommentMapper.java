package com.VoxPopuli.CommentService.mappers;

import java.util.UUID;

import com.VoxPopuli.CommentService.domain.Comment;
import com.VoxPopuli.commentcontracts.CommentRequest;
import com.VoxPopuli.commentcontracts.CommentResponse;

public final class CommentMapper {
    private CommentMapper() {
    }

    public static final CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId().toString())
                .parentId(comment.getParentId().toString())
                .userId(comment.getUserId().toString())
                .content(comment.getContent())
                .updatedAt(comment.getLastUpdated())
                .build();
    }

    public static final Comment fromRequest(CommentRequest request) {
        return Comment.builder()
                .parentId(UUID.fromString(request.getParentId()))
                .userId(UUID.fromString(request.getUserId()))
                .sourceLinkHash(request.getSourceLinkHash())
                .lastUpdated(request.getUpdatedAt())
                .build();
    }
}
