package com.foundvio.controller

import com.foundvio.clouddb.model.User
import com.foundvio.service.KafkaProducer
import com.foundvio.service.UserService
import com.foundvio.utils.Logging
import com.foundvio.utils.Response
import com.foundvio.utils.logger
import com.huawei.agconnect.server.auth.exception.AGCAuthException
import com.huawei.agconnect.server.auth.service.AGCAuth
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class UserController(
    val userService: UserService,
    val producer: KafkaProducer
) : Logging{

    private val logger = logger()

    @GetMapping
    fun index() = Response.Success()

    @PostMapping("registerUser")
    fun registerUser(
                @RequestParam isTracker: Boolean,
                @RequestParam phone: String,
                @RequestParam familyName: String,
                @RequestParam givenName: String,
    ): Response{
        return try{
            val user = User().apply {
                id = UUID.randomUUID().toString()
                this.phone = phone
                this.familyName = familyName
                this.givenName = givenName
                this.isTracker = isTracker
            }
            userService.upsertUser(user)
            Response.Success()
        }catch (e: Exception){
            Response.Error("Unable to register user")
        }
    }


    @GetMapping("/test")
    fun sendMessageToKafkaTopic(@RequestParam message: String): String {
        this.producer.sendMessage(message)
        return "Success"
    }

}