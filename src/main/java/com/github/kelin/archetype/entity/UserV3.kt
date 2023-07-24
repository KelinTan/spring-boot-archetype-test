package com.github.kelin.archetype.entity

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document(collection = EntityCollections.USER_V3)
data class UserV3(
    @Id
    var id: String = "",
    var name: String? = null
) {
    constructor(name: String) : this("", name)
}
