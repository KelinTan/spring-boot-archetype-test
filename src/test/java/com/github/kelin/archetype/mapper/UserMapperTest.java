package com.github.kelin.archetype.mapper;

import static com.github.kelin.archetype.support.TestConstants.USER_SQL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.kelin.archetype.entity.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(USER_SQL)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserById() {
        User user = userMapper.getUserById(1L);

        assertEquals(user.getId(), 1L);
        assertEquals(user.getName(), "test");
    }

    @Test
    public void insertUser() {
        User user = new User("test2");
        userMapper.insertUser(user);

        assertTrue(user.getId() > 0);
        assertEquals(user.getName(), "test2");
    }
}
