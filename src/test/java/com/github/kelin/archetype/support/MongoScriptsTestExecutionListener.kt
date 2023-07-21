@file:Suppress("SpellCheckingInspection")

package com.github.kelin.archetype.support

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestContextAnnotationUtils
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.test.context.util.TestContextResourceUtils
import java.nio.file.Files

@Component
class MongoScriptsTestExecutionListener : AbstractTestExecutionListener() {
    override fun beforeTestMethod(testContext: TestContext) {
        val testClass = testContext.testClass
        val mongoTemplate = testContext.applicationContext.getBean(MongoTemplate::class.java)
        mongoTemplate.db.drop()
        getScriptAnnotaions(testClass).forEach { annotation ->
            val scripts =
                TestContextResourceUtils.convertToClasspathResourcePaths(testContext.testClass, *annotation.value)
            TestContextResourceUtils.convertToResourceList(
                testContext.applicationContext, *scripts
            ).forEach {
                val lines = Files.readAllLines(it.file.toPath())
                mongoTemplate.insert(lines, it.file.nameWithoutExtension)
            }
        }
    }

    override fun afterTestMethod(testContext: TestContext) {
    }

    private fun getScriptAnnotaions(clazz: Class<*>): Set<MongoScript> {
        return TestContextAnnotationUtils.getMergedRepeatableAnnotations(clazz, MongoScript::class.java)
    }
}
