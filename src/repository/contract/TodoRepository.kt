package com.alban.todoserver.repository.contract

import com.alban.todoserver.models.Todo

interface TodoRepository {
    suspend fun getAll(): List<Todo?>
    suspend fun findTodoById(id: Int): Todo?
    suspend fun addTodo(todo: Todo): Todo?
    suspend fun updateTodo(id: Int, todo: Todo): Todo?
    suspend fun deleteTodo(id: Int): Boolean
}