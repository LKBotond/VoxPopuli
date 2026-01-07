package com.VoxPopuli.AuthenticationService.dtos;

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
    private String email;
    private String alias;
    private String passHash;
}
