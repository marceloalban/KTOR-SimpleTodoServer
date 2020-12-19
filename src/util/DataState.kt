package com.alban.todoserver.util

sealed class DataState {
    data class Success(val data: Any?) {
        val message = "OK"
    }

    data class Error(val errorMessage: String?) {
        val message = "ERROR"
    }
}