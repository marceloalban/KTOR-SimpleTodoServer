package com.alban.todoserver.entity

import com.alban.todoserver.models.Todo
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object TodoEntity : Table("Todo") {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val todo = varchar("todo", 512)
    val done = bool("done")
}

fun ResultRow?.toModelTodo(): Todo? {
    if (this == null) {
        return null
    }

    return Todo(
        id = this[TodoEntity.id],
        todo = this[TodoEntity.todo],
        done = this[TodoEntity.done]
    )
}