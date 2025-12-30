package com.VoxPopuli.AuthenticationService.services;

import java.nio.CharBuffer;
import java.util.Arrays;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Argon2PassHashingService {
    private final Argon2PasswordEncoder encoder;

    public String hashWithArgon2(char[] password) {
        CharBuffer wrappedPass = wrapPass(password);
        try {
            return encoder.encode(wrappedPass);
        } finally {
            wipeSensitiveMemory(wrappedPass);
        }
    }

    public boolean verifyPass(char[] password, String passOnRecord) {
        CharBuffer wrappedPass = wrapPass(password);
        try {
            return encoder.matches(wrappedPass, passOnRecord);
        } finally {
            wipeSensitiveMemory(wrappedPass);
        }
    }

    private CharBuffer wrapPass(char[] password) {
        return CharBuffer.wrap(password);
    }

    private void wipeSensitiveMemory(CharBuffer sensitive) {
        char[] accesible = sensitive.array();
        Arrays.fill(accesible, '\0');
        sensitive.clear();
    }
}
