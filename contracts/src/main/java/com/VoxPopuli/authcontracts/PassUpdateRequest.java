package com.VoxPopuli.authcontracts;

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
public class PassUpdateRequest {
    private char[] newPass;
    private char[] oldPass;
}
