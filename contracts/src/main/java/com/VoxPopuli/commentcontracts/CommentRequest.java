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
public class CommentRequest {
    String parentId;
    String userId;
    String content;
    String sourceLinkHash;
    OffsetDateTime updatedAt;

}
