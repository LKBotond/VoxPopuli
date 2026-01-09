package com.VoxPopuli.Gateway.dtos.sessionClient;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionToken {
    private String sessionId;
    private String alias;
}