package com.VoxPopuli.Users.dto;

import com.VoxPopuli.Users.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String email;
    private String alias;
    private String passHash;

    public User mapToUser() {
        return User.builder()
                .email(this.email)
                .alias(this.alias)
                .passHash(this.passHash)
                .build();
    }
}
