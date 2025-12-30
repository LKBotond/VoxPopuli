package com.VoxPopuli.AuthenticationService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashedRegistrationRequest {
    private String email;
    private String alias;
    private String passHash;
}
