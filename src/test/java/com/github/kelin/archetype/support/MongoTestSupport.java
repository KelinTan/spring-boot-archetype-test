package com.github.kelin.archetype.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestExecutionListeners;

@SuppressWarnings("SpellCheckingInspection")
@TestExecutionListeners(
        listeners = {MongoScriptsTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class MongoTestSupport {
    @Autowired
    protected MongoTemplate mongoTemplate;
}
