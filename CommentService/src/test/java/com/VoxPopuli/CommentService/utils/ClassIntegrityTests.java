package com.VoxPopuli.CommentService.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;



public final class ClassIntegrityTests {
    private ClassIntegrityTests() {
    }

    public static void testObjectIntegrity(Object user) {
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
