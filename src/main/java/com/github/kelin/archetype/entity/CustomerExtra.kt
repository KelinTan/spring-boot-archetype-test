package com.github.kelin.archetype.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = EntityCollections.CUSTOMER_EXTRA)
class CustomerExtra {
    @Id
    var id: String? = null
    var firstName: String? = null
    @JvmField
    var data: String? = null

    constructor()
    constructor(data: String?) {
        this.data = data
    }

    constructor(firstName: String?, data: String?) {
        this.firstName = firstName
        this.data = data
    }
}
