package com.github.kelin.archetype.controller;

import com.github.kelin.archetype.entity.User;
import com.github.kelin.archetype.mapper.UserMapper;
import com.github.kelin.archetype.mapper.UserV2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class GreetingControllerV2 {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserV2Mapper userV2Mapper;

    @GetMapping("/greeting")
    public User greeting(@RequestParam(value = "id") Long id) {
        return userMapper.getUserById(id);
    }

    @GetMapping("/greeting2")
    public User greeting2(@RequestParam(value = "id") Long id) {
        return userV2Mapper.getUserById(id);
    }
}
