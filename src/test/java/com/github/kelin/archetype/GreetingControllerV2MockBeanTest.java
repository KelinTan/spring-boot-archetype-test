package com.github.kelin.archetype;

import static org.mockito.Mockito.when;

import com.github.kelin.archetype.controller.GreetingControllerV2;
import com.github.kelin.archetype.entity.User;
import com.github.kelin.archetype.mapper.UserMapper;
import com.github.kelin.archetype.mapper.UserV2Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@WebMvcTest(GreetingControllerV2.class)
public class GreetingControllerV2MockBeanTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserV2Mapper userV2Mapper;
    @MockBean
    private UserMapper userMapper;

    @Test
    void testGreeting() throws Exception {
        when(userMapper.getUserById(1L)).thenReturn(new User(1L, "mock"));

        mvc.perform(MockMvcRequestBuilders.get("/v2/greeting").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{'id':1,'name':'mock'}"));
    }

    @Test
    void testGreeting2() throws Exception {
        when(userV2Mapper.getUserById(1L)).thenReturn(new User(1L, "mock2"));

        mvc.perform(MockMvcRequestBuilders.get("/v2/greeting2").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{'id':1,'name':'mock2'}"));
    }
}
