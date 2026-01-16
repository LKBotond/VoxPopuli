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
                .userId(comment.getUserId() != null
                        ? comment.getUserId().toString()
                        : "[deleted]")
                .content(comment.getContent() != null
                        ? comment.getContent()
                        : "[deleted]")
                .updatedAt(comment.getLastUpdated())
                .build();
    }

    public static final Comment fromRequest(CommentRequest request) {
        return Comment.builder()
                .parentId(UUID.fromString(request.getParentId()))
                .userId(UUID.fromString(request.getUserId()))
                .content(request.getContent())
                .sourceLinkHash(request.getSourceLinkHash())
                .lastUpdated(request.getUpdatedAt())
                .build();
    }
}
