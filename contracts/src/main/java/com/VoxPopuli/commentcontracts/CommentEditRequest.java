package com.VoxPopuli.commentcontracts;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "CommentId is required")
    String commentId;
    @NotBlank(message = "EditedContent is required")
    String editedContent;
}
