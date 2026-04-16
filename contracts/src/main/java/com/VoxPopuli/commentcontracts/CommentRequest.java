package com.VoxPopuli.commentcontracts;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "ParentId is required")
    String parentId;
    @NotBlank(message = "alias is required")
    String alias;
    @NotBlank(message = "Content is required")
    String content;
    @NotBlank(message = "SourceLinkHash is required")
    String sourceLinkHash;
    @NotNull(message = "UpdatedAt is required")
    OffsetDateTime updatedAt;

}
