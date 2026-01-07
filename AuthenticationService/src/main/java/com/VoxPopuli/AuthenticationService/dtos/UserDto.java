package com.VoxPopuli.AuthenticationService.dtos;

import java.util.UUID;

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
public class UserDto {
    private UUID userId;
    private String alias;
    private String passHash;

    public LoginResponse mapToLoginResponse() {
        return LoginResponse.builder().userId(userId).alias(alias).build();
    }
}