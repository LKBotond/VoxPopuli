package com.VoxPopuli.Gateway.dtos.userClient;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private UUID userId;
    private String alias;
    private String hashedPass;
}