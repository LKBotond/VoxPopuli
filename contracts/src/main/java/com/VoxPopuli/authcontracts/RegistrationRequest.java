package com.VoxPopuli.authcontracts;

import jakarta.validation.constraints.NotBlank;
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
public class RegistrationRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Alias is required")
    private String alias;
    
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 char long")
    private char[] passArray;
}