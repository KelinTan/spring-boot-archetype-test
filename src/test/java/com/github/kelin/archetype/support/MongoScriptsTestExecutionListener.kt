@file:Suppress("SpellCheckingInspection")

package com.github.kelin.archetype.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestContextAnnotationUtils
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.test.context.util.TestContextResourceUtils
import java.io.File
import java.nio.file.Files

@Component
class MongoScriptsTestExecutionListener : AbstractTestExecutionListener() {
    private val objectMapper = ObjectMapper()

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
                loadScript(it.file, mongoTemplate)
            }
        }
    }

    override fun afterTestMethod(testContext: TestContext) {
    }

    private fun loadScript(script: File, mongoTemplate: MongoTemplate) {
        if (script.name.endsWith(EXT_BSON)) {
            val lines = Files.readAllLines(script.toPath())
            mongoTemplate.insert(lines, script.nameWithoutExtension)
        } else if (script.name.endsWith(EXT_JSON)) {
            val scriptContent = script.readText().trim()
            if (scriptContent.startsWith("[") && scriptContent.endsWith("]")) {
                val documents = objectMapper.readValue<List<Document>>(
                    script.readText(),
                    objectMapper.typeFactory.constructCollectionType(
                        List::class.java, Document::class.java
                    )
                )
                mongoTemplate.insert(documents, script.nameWithoutExtension)
            } else {
                val document = objectMapper.readValue(script.readText(), Document::class.java)
                mongoTemplate.insert(document, script.nameWithoutExtension)
            }
        } else {
            // TODO support other script type
            throw IllegalArgumentException("Unsupported script type: ${script.name}")
        }
    }

    private fun getScriptAnnotaions(clazz: Class<*>): Set<MongoScript> {
        return TestContextAnnotationUtils.getMergedRepeatableAnnotations(clazz, MongoScript::class.java)
    }

    companion object {
        private const val EXT_BSON = "bson"
        private const val EXT_JSON = "json"
    }
}
