package com.alban.todoserver.models

data class Todo(
    val id: Int,
    val todo: String,
    val done: Boolean
)