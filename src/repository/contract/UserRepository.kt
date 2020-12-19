package com.alban.todoserver.repository.contract

import com.alban.todoserver.models.User

interface UserRepository {
    suspend fun findUserByEmail(email: String): User?
    suspend fun addUser(user: User): User?
    suspend fun generateToken(email: String, password: String): String?
}