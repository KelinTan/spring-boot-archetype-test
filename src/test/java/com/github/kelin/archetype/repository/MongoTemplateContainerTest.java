package com.github.kelin.archetype.repository;

import com.github.kelin.archetype.BaseMongoContainerTest;
import com.github.kelin.archetype.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@SuppressWarnings("SpellCheckingInspection")
public class MongoTemplateContainerTest extends BaseMongoContainerTest {
    @Override
    protected void initData() {
        mongoTemplate.save(new User("test"), "user");
    }

    @Test
    public void test() {
        User saved = mongoTemplate.save(new User("test"), "user");
        mongoTemplate.findById(saved.getId(), User.class, "user");
    }
}
