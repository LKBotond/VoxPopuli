package com.VoxPopuli.Users.integrationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.helpers.UserMapper;
import com.VoxPopuli.Users.repository.UserRepository;
import com.VoxPopuli.Users.utils.ClassIntegrityTests;
import com.VoxPopuli.Users.utils.TestDataUtils;

@SpringBootTest
@Transactional
public class UserRepoIntegrationTests {

    @Autowired
    private UserRepository repositoryTest;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCreation() {
        User savedUser = repositoryTest.save(TestDataUtils.createTestUser());
        assertNotNull(savedUser);
        ClassIntegrityTests.testObjectIntegrity(savedUser);
    }

    @Test
    public void testRegistrationDtoToUserDomainMapping() {
        RegistrationRequest request = TestDataUtils.createTestRegistrationRequest();
        User user = userMapper.registrationRequestToUser(request);
        try {
            testIdLessUserDomains(user);
        } catch (IllegalAccessException e) {
            fail("Failed to access field error: ", e);
        }
    }

    @Test
    public void succesfulLogin() {
        User testUser = saveTestUser();
        Optional<User> user = repositoryTest.findByEmail(testUser.getEmail());
        assertTrue(user.isPresent());
        ClassIntegrityTests.testObjectIntegrity(user.get());
    }

    @Test
    public void failedLogin() {
        Optional<User> user = repositoryTest.findByEmail("random@random.com");
        assertTrue(user.isEmpty());
    }

    @Test
    public void testDeletion() {
        User testUser = saveTestUser();
        Optional<User> user = repositoryTest.findByEmail(testUser.getEmail());
        User present = user.get();
        repositoryTest.delete(present);
        assertTrue(repositoryTest.findByAlias(present.getAlias()).isEmpty());
    }

    private void testIdLessUserDomains(User user) throws IllegalAccessException {
        Class<?> userClass = user.getClass();
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields) {
            if ("userID".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(user);
            assertNotNull(value, "Field '" + field.getName() + "' should not be null");
        }
    }

    private User saveTestUser() {
        return repositoryTest.save(TestDataUtils.createTestUser());
    }

}
