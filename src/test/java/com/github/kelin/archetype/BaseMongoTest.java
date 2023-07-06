package com.github.kelin.archetype;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

@SuppressWarnings("SpellCheckingInspection")
public class BaseMongoTest {
    @Autowired
    protected MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        mongoTemplate.getDb().drop();
        initData();
    }

    protected void initData() {
    }
}
