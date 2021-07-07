package com.foundvio.utils


open class Response<out T>(val message: T, val status: String) {

    class Success<T>(message: T): Response<T>(message, "Success")
    class Error<T>(message: T): Response<T>(message, "Error")

    companion object {
        fun Success(message: String = "complete") = Success<String>(message)
    }
}