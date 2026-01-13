package com.VoxPopuli.commentcontracts;

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
public class CommentEditRequest {
    String commentId;
    String editedContent;
}
