package com.github.kelin.archetype.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = EntityCollections.CUSTOMER_RECORDS)
class CustomerRecord {
    @Id
    var id: String? = null
    var firstName: String? = null
    @JvmField
    var record: String? = null

    constructor(firstName: String?, record: String?) {
        this.firstName = firstName
        this.record = record
    }

    constructor()
    constructor(record: String?) {
        this.record = record
    }
}
