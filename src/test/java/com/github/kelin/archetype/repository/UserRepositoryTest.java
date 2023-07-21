package com.github.kelin.archetype.repository;

import static com.github.kelin.archetype.support.TestConstants.USER_SQL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.kelin.archetype.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(USER_SQL)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById() {
        Optional<User> user = userRepository.findById(1L);

        assertNotNull(user.orElse(null));
        assertEquals(user.get().getId(), 1L);
        assertEquals(user.get().getName(), "test");
    }

    @Test
    public void save() {
        User user = new User("test2");
        userRepository.save(user);

        assertTrue(user.getId() > 0);
        assertEquals(user.getName(), "test2");
    }
}
