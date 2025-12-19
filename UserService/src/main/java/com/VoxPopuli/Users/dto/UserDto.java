package com.VoxPopuli.Users.dto;

import java.util.UUID;

import com.VoxPopuli.Users.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID user_id;
    private String alias;

    public void mapUserToDTO(User user) {
        this.user_id = user.getUserID();
        this.alias = user.getAlias();
    }
}
