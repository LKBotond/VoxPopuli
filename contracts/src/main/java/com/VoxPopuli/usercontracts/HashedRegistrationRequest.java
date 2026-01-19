package com.VoxPopuli.usercontracts;

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
public class HashedRegistrationRequest {
     @NotBlank(message = "Email is required")
    private String email;
     @NotBlank(message = "Alias is required")
    private String alias;
     @NotBlank(message = "PassHash is required")
    private String passHash;
}
