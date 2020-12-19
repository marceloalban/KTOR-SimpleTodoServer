package com.alban.todoserver.repository

import com.alban.todoserver.entity.TodoEntity
import com.alban.todoserver.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

object DatabaseFactory {

    fun init() {
        //Connection with sqlite
        //data.db will be created at root folder
        Database.connect("jdbc:sqlite:./data.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            //Configure logs
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(TodoEntity)
            SchemaUtils.create(UserEntity)
        }
    }

}

suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }
