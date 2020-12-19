package com.alban.todoserver.models

import io.ktor.auth.*

class User(
    val id : Int,
    val email: String,
    val name: String,
    val password: String
) : Principal