package com.github.kelin.archetype.controller;

import com.github.kelin.archetype.entity.User;
import com.github.kelin.archetype.mapper.UserMapper;
import com.github.kelin.archetype.mapper.UserV2Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class GreetingControllerV2 {

    private final UserMapper userMapper;

    private final UserV2Mapper userV2Mapper;

    public GreetingControllerV2(UserMapper userMapper, UserV2Mapper userV2Mapper) {
        this.userMapper = userMapper;
        this.userV2Mapper = userV2Mapper;
    }

    @GetMapping("/greeting")
    public User greeting(@RequestParam(value = "id") Long id) {
        return userMapper.getUserById(id);
    }

    @GetMapping("/greeting2")
    public User greeting2(@RequestParam(value = "id") Long id) {
        return userV2Mapper.getUserById(id);
    }
}
