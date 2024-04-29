package uz.architect.springbootadvanced.authuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthUserRepositoryTest {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Test
    void createAuthUser() {
        AuthUser user = AuthUser.builder()
                .fullName("John Doe")
                .username("johnny")
                .password("123")
                .build();
        AuthUser savedUser = authUserRepository.save(user);
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), user.getPassword());
        assertEquals(savedUser.getFullName(), user.getFullName());
        assertEquals(savedUser.getId(), user.getId());
    }

    @Test
    void testSaveAuthUserWithFail() {
        AuthUser authUser = AuthUser.builder()
                .fullName("Tom Hanks")
                .password("321")
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> authUserRepository.save(authUser))
                .printStackTrace();
    }

    @Test
    void findById() {
        AuthUser user = authUserRepository.findById(1).orElse(null);
        assertNotNull(user);
        assertEquals("johnny", user.getUsername());
        assertEquals("123", user.getPassword());
    }

    @Test
    @Sql("classpath:/insert.sql")
    void multiSave() {
        long count = authUserRepository.count();
        assertEquals(4, count);
    }
}