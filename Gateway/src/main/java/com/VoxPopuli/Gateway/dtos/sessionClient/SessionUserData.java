package com.VoxPopuli.Gateway.dtos.sessionClient;

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
public class SessionUserData {
    private UUID userId;
    private String alias;
}