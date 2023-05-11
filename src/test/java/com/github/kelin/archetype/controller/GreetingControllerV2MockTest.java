package com.github.kelin.archetype.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.github.kelin.archetype.entity.User;
import com.github.kelin.archetype.mapper.UserMapper;
import com.github.kelin.archetype.mapper.UserV2Mapper;
import com.github.kelin.archetype.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GreetingControllerV2MockTest {
    @InjectMocks
    private GreetingControllerV2 greetingControllerV2;
    @Mock
    private UserV2Mapper userV2Mapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    @Test
    void testGreeting() {
        when(userMapper.getUserById(1L)).thenReturn(new User(1L, "mock"));

        User user = greetingControllerV2.greeting(1L);
        assertEquals(user, new User(1L, "mock"));
    }

    @Test
    void testGreeting2() {
        when(userV2Mapper.getUserById(1L)).thenReturn(new User(1L, "mock2"));

        User user = greetingControllerV2.greeting2(1L);
        assertEquals(user, new User(1L, "mock2"));
    }
}
