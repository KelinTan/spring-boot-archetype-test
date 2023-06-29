package com.github.kelin.archetype.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.kelin.archetype.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

@DataMongoTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void save() {
        customerRepository.save(new Customer("Alice", "Smith"));

        List<Customer> byFirstName = customerRepository.findByFirstName("Alice");
        assertEquals(1, byFirstName.size());
        assertEquals("Alice", byFirstName.get(0).getFirstName());
    }
}
