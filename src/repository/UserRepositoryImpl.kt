package com.alban.todoserver.repository

import com.alban.todoserver.auth.makeToken
import com.alban.todoserver.entity.UserEntity
import com.alban.todoserver.entity.toModelUser
import com.alban.todoserver.models.User
import com.alban.todoserver.repository.contract.UserRepository
import io.ktor.auth.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserRepositoryImpl : UserRepository {
    override suspend fun findUserByEmail(email: String): User? = dbQuery {
        UserEntity.select { UserEntity.email eq email }.singleOrNull().toModelUser()
    }

    override suspend fun addUser(user: User): User? {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UserEntity.insert {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
            }
        }

        return statement?.resultedValues?.get(0).toModelUser()
    }

    override suspend fun generateToken(email: String, password: String): String? = dbQuery {
        val user = UserEntity.select { (UserEntity.email eq email) and (UserEntity.password eq password) }
            .singleOrNull()
            .toModelUser()

        user?.let {
            return@dbQuery makeToken(it)
        }
        return@dbQuery null
    }
}