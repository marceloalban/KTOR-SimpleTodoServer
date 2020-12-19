package com.alban.todoserver.entity

import com.alban.todoserver.models.User
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object UserEntity : Table("User") {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val email = varchar("email", 80).uniqueIndex()
    val name = varchar("name", 30)
    val password = varchar("password", 100)
}

fun ResultRow?.toModelUser(): User? {
    if (this == null) {
        return null
    }

    return User(
        id = this[UserEntity.id],
        name = this[UserEntity.name],
        email = this[UserEntity.email],
        password = ""
    )
}