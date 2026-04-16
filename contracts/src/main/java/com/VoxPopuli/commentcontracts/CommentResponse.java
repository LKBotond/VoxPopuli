package com.VoxPopuli.commentcontracts;

import java.time.OffsetDateTime;

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
public class CommentResponse {
    String commentId;
    String parentId;
    String alias;
    String content;
    OffsetDateTime updatedAt;
}
