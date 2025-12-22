package com.VoxPopuli.CommentService.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.VoxPopuli.CommentService.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    UUID parentId;
    UUID userId;
    String content;
    String sourceLinkHash;
    OffsetDateTime updatedAt;

    public Comment mapToComment() {
        return Comment.builder()
                .parentId(this.parentId)
                .userId(this.userId)
                .content(content)
                .sourceLinkHash(this.sourceLinkHash)
                .lastUpdated(this.updatedAt)
                .build();
    }
}
