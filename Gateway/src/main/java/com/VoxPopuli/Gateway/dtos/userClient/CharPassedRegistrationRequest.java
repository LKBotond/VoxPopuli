package com.VoxPopuli.Gateway.dtos.userClient;

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
public class CharPassedRegistrationRequest {
    private String email;
    private String alias;
    private char[] pass;
}
