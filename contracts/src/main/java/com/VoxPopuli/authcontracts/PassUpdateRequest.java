package com.VoxPopuli.authcontracts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PassUpdateRequest {

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 char long")
    private char[] newPass;
    
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 char long")
    private char[] oldPass;
}
