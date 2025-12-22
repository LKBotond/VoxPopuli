package com.VoxPopuli.CommentService.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    UUID commentId;
    UUID parentId;
    UUID userId;
    String content;
    OffsetDateTime updatedAt;
}
