package com.alban.todoserver.repository

import com.alban.todoserver.entity.TodoEntity
import com.alban.todoserver.entity.toModelTodo
import com.alban.todoserver.models.Todo
import com.alban.todoserver.repository.contract.TodoRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class TodoRepositoryImpl : TodoRepository {
    override suspend fun getAll(): List<Todo?> = dbQuery {
        TodoEntity.selectAll().map { it.toModelTodo() }
    }

    override suspend fun findTodoById(id: Int): Todo? = dbQuery {
        TodoEntity.select { TodoEntity.id eq id }.singleOrNull().toModelTodo()
    }

    override suspend fun addTodo(todo: Todo): Todo? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = TodoEntity.insert {
                it[TodoEntity.todo] = todo.todo
                it[done] = todo.done
            }
        }

        return statement?.resultedValues?.get(0).toModelTodo()
    }

    override suspend fun updateTodo(id: Int, todo: Todo): Todo? = dbQuery {
        TodoEntity.update({ TodoEntity.id eq id }) {
            it[TodoEntity.todo] = todo.todo
            it[done] = todo.done
        }

        return@dbQuery TodoEntity.select { TodoEntity.id eq id }.single().toModelTodo()
    }

    override suspend fun deleteTodo(id: Int): Boolean = dbQuery {
        TodoEntity.deleteWhere { TodoEntity.id eq id } > 0
    }
}