package com.foundvio.utils

open class Response(val message: String, val status: String) {

    class Success(message: String = "complete"): Response(message, "Success")
    class Error(message: String): Response(message, "Error")

}