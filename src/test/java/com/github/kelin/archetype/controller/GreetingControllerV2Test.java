package com.github.kelin.archetype.controller;

import static com.github.kelin.archetype.support.TestConstants.USER_SQL;
import static com.github.kelin.archetype.support.TestConstants.USER_V2_SQL;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.kelin.archetype.entity.Customer;
import com.github.kelin.archetype.repository.CustomerRepository;
import com.github.kelin.archetype.support.BaseMongoTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({@Sql(USER_SQL), @Sql(USER_V2_SQL)})
public class GreetingControllerV2Test extends BaseMongoTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testGreeting() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v2/greeting").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id':1,'name':'test'}"));
    }

    @Test
    void testGreeting2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v2/greeting2").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id':1,'name':'test_v2'}"));
    }

    @Test
    void testGreeting3() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v2/greeting3").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test")));
    }

    @Test
    void testGreeting4() throws Exception {
        Customer saved = customerRepository.save(new Customer("Alice", "Smith"));

        mvc.perform(MockMvcRequestBuilders.get("/v2/greeting4").param("id", saved.id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.id)))
                .andExpect(jsonPath("$.firstName", is("Alice")));
    }
}
