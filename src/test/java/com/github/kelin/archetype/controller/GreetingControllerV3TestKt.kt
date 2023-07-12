package com.github.kelin.archetype.controller

import com.github.kelin.archetype.BaseMongoTest
import com.github.kelin.archetype.TestConstants
import com.github.kelin.archetype.entity.Customer
import com.github.kelin.archetype.repository.CustomerRepository
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup(Sql(TestConstants.USER_DATA), Sql(TestConstants.USER_V2_DATA))
class GreetingControllerV3TestKt : BaseMongoTest() {
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Test
    fun testGreeting() {
        mvc.perform(MockMvcRequestBuilders.get("/v3/greeting").param("id", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                {'id':1,
                'name':'test'
                }
                    """.trimIndent()
                )
            )
    }

    @Test
    fun testGreeting2() {
        mvc.perform(MockMvcRequestBuilders.get("/v3/greeting2").param("id", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                {'id':1,
                'name':'test_v2'
                }
                    """.trimIndent()
                )
            )
    }

    @Test
    fun testGreeting3() {
        mvc.perform(MockMvcRequestBuilders.get("/v3/greeting3").param("id", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`("test")))
    }

    @Test
    fun testGreeting4() {
        val saved = customerRepository.save(Customer("Alice", "Smith"))
        mvc.perform(MockMvcRequestBuilders.get("/v3/greeting4").param("id", saved.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(saved.getId())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.`is`("Alice")))
    }
}
