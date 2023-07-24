package com.github.kelin.archetype.repository;

import static com.github.kelin.archetype.support.TestConstants.USER_V3_JSON;

import com.github.kelin.archetype.entity.User;
import com.github.kelin.archetype.entity.UserV3;
import com.github.kelin.archetype.support.BaseMongoContainerTest;
import com.github.kelin.archetype.support.MongoScript;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@SuppressWarnings("SpellCheckingInspection")
@MongoScript(USER_V3_JSON)
@DataMongoTest
public class MongoTemplateContainerTest extends BaseMongoContainerTest {
    @Test
    public void test() {
        UserV3 saved = mongoTemplate.save(new UserV3("test"), "user");
        mongoTemplate.findById(saved.getId(), User.class, "user");
    }
}
