package com.alban.todoserver.routes

import com.alban.todoserver.models.User
import com.alban.todoserver.repository.contract.UserRepository
import com.alban.todoserver.util.DataState
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoutes(
    db: UserRepository
) {
    route("user/") {
        post("/") {
            val userReceived = call.receive<User>()

            try {
                val userCreated = db.addUser(userReceived)
                userCreated?.let {
                    call.respond(DataState.Success(it))
                }
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }

        post("/generateToken") {
            val userReceived = call.receive<User>()

            try {
                val token = db.generateToken(userReceived.email, userReceived.password)
                token?.let {
                    call.respond(DataState.Success(it))
                    return@post
                }

                return@post call.respond(DataState.Error("We were unable to generate the token"))
            } catch (E: Exception) {
                call.respond(DataState.Error(E.message))
            }
        }
    }
}