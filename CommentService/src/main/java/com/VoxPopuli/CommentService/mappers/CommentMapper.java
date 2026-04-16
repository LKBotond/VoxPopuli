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
                                .alias(comment.getAlias())
                                .content(comment.getContent() != null
                                                ? comment.getContent()
                                                : "[deleted]")
                                .updatedAt(comment.getLastUpdated())
                                .build();
        }

        public static final Comment fromRequest(CommentRequest request, UUID userId) {
                return Comment.builder()
                                .parentId(UUID.fromString(request.getParentId()))
                                .userId(userId)
                                .alias(request.getAlias())
                                .content(request.getContent())
                                .sourceLinkHash(request.getSourceLinkHash())
                                .lastUpdated(request.getUpdatedAt())
                                .build();
        }
}
