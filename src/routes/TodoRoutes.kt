package com.alban.todoserver.routes

import com.alban.todoserver.models.Todo
import com.alban.todoserver.repository.contract.TodoRepository
import com.alban.todoserver.util.DataState
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.todoRoutes(
    db: TodoRepository
) {
    route("todo/") {
        get("/") {
            try {
                call.respond(
                    DataState.Success(db.getAll())
                )
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0
            if (id == 0) {
                return@get call.respond(DataState.Error("Did you forget the id?"))
            }

            try {
                call.respond(
                    DataState.Success(db.findTodoById(id))
                )
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }

        post("/") {
            val todoReceived = call.receive<Todo>()

            try {
                val todoCreated = db.addTodo(todoReceived)
                todoCreated?.let {
                    call.respond(DataState.Success(it))
                }
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }

        put("/{id}") {
            val todoReceived = call.receive<Todo>()
            val id = call.parameters["id"]?.toInt() ?: 0

            if (id == 0) {
                return@put call.respond(DataState.Error("Did you forget the id?"))
            }

            try {
                val todoUpdated = db.updateTodo(id, todoReceived)
                todoUpdated?.let {
                    call.respond(DataState.Success(it))
                }
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: 0

            if (id == 0) {
                return@delete call.respond(DataState.Error("Did you forget the id?"))
            }

            try {
                val isDeleted = db.deleteTodo(id)
                if (isDeleted) {
                    call.respond(DataState.Success("Ok"))
                } else {
                    call.respond(DataState.Success("We can't find the object"))
                }
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }
    }
}