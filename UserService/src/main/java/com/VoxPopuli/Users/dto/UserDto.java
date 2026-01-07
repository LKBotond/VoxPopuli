package com.VoxPopuli.Users.dto;

import java.util.UUID;

import com.VoxPopuli.Users.domain.User;

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

    public void mapUserToDTO(User user) {
        this.userId = user.getUserID();
        this.alias = user.getAlias();
        this.passHash=user.getPassHash();
    }
}
