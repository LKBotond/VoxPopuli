package com.VoxPopuli.sessioncontracts;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserData {
    @NotBlank(message = "UserId is required")
    private String userId;
    @NotBlank(message = "Alias is required")
    private String alias;
}
