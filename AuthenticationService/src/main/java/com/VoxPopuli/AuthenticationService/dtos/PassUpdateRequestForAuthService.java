package com.VoxPopuli.AuthenticationService.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassUpdateRequestForAuthService {
        private char[] oldPassArray;
        private char[] newPassArray;
}
