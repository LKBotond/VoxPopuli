package com.VoxPopuli.Gateway.dtos.commetnClient;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    UUID parentId;
    UUID userId;
    String content;
    String sourceLinkHash;
    OffsetDateTime updatedAt;
}
