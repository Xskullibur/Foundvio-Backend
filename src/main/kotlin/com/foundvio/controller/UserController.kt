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
                @RequestHeader("access-token") accessToken: String,
                @RequestParam isTracker: Boolean,
                @RequestParam phone: String,
                @RequestParam familyName: String,
                @RequestParam givenName: String,
    ): Response<Any> {
        return try{
            val agcAuth = AGCAuth.getInstance()
            val accessTokenResult = agcAuth.verifyAccessToken(accessToken, true)
            val user = User().apply {
                this.id = accessTokenResult.sub
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

    @GetMapping("userDetails")
    fun userDetails(@RequestHeader("access-token") accessToken: String): Response<Any>{
        val agcAuth = AGCAuth.getInstance()
        val accessTokenResult = agcAuth.verifyAccessToken(accessToken, true)
        val user = userService.queryUserById(accessTokenResult.sub)
        return if(user != null){
            Response.Success(user)
        }else{
            Response.Error("Unable to find user")
        }
    }



    @GetMapping("/test")
    fun sendMessageToKafkaTopic(@RequestParam message: String): String {
        this.producer.sendMessage(message)
        return "Success"
    }

}