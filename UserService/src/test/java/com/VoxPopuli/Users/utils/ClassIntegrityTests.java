package com.VoxPopuli.Users.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import com.VoxPopuli.Users.domain.User;

public final class ClassIntegrityTests {
    private ClassIntegrityTests() {
    }

    public static void testObjectIntegrity(User user) {
        Class<?> userClass = user.getClass();
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(user);
                assertNotNull(value);
            } catch (IllegalAccessException e) {
                fail("Failed to access field, error:", e);
            }
        }
    }
}
