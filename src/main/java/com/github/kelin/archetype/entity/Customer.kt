package com.github.kelin.archetype.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = EntityCollections.CUSTOMERS)
class Customer {
    @JvmField
    @Id
    var id: String? = null
    @JvmField
    var firstName: String? = null
    @JvmField
    var lastName: String? = null
    @JvmField
    var position = 0

    @JvmField
    @ReadOnlyProperty
    var records: List<CustomerRecord>? = null

    @JvmField
    @ReadOnlyProperty
    var extra: CustomerExtra? = null

    constructor()
    constructor(firstName: String?, lastName: String?) {
        this.firstName = firstName
        this.lastName = lastName
    }

    constructor(firstName: String?, lastName: String?, position: Int) {
        this.firstName = firstName
        this.lastName = lastName
        this.position = position
    }
}
