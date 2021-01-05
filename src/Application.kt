package com.alban.todoserver

import com.alban.todoserver.repository.DatabaseFactory
import com.alban.todoserver.repository.TodoRepositoryImpl
import com.alban.todoserver.repository.UserRepositoryImpl
import com.alban.todoserver.routes.todoRoutes
import com.alban.todoserver.routes.userRoutes
import com.alban.todoserver.util.DataState
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()

    val dbTodo = TodoRepositoryImpl()
    val dbUser = UserRepositoryImpl()

    install(Authentication) {
        jwt {
            verifier(com.alban.todoserver.auth.verifier)
            realm = "ktor.io"
            validate {
                val emailFromPayload = it.payload.getClaim("email").asString() ?: ""
                dbUser.findUserByEmail(emailFromPayload)
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, DataState.Error("Unauthorized"))
            }
        }
    }

    install(ContentNegotiation) {
        gson {}
    }


    routing {
        userRoutes(dbUser)
        authenticate {
            todoRoutes(dbTodo)
        }
    }
}