package com.VoxPopuli.AuthenticationService.dtos;

import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID user_id;
    private String alias;
    private String passHash;

    public LoginResponse mapToLoginResponse() {
        return LoginResponse.builder().userId(user_id).alias(alias).build();
    }
}