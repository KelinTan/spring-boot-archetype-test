package com.github.kelin.archetype.entity

import java.util.Objects
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var name: String? = null

    constructor()
    constructor(id: Long, name: String?) {
        this.id = id
        this.name = name
    }

    constructor(name: String?) {
        this.name = name
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val user = o as User
        return id == user.id && name == user.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }
}
