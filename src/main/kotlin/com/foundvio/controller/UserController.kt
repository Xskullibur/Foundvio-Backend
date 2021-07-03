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


@RestController
class UserController(
    val userService: UserService,
    val producer: KafkaProducer
) : Logging{

    private val logger = logger()

    @GetMapping
    fun index() = Response.Success()

    @PostMapping("addUser")
    fun addUser(@RequestHeader("access-token") accessToken: String,
                @RequestParam isTracker: Boolean): Response{
        val agcAuth = AGCAuth.getInstance()
        return try{
            val agcAccessToken = agcAuth.verifyAccessToken(accessToken, true)
            val user = User().apply {
                phoneId = agcAccessToken.phone
                familyName = agcAccessToken.name
                givenName = agcAccessToken.name
                this.isTracker = isTracker
            }
            userService.upsertUser(user)
            Response.Success()
        }catch (e: AGCAuthException){
            logger.error("Invalid access token, error code: ${e.errorCode}")
            Response.Error("Unable to verify access token")
        }
    }


    @GetMapping("/test")
    fun sendMessageToKafkaTopic(@RequestParam message: String): String {
        this.producer.sendMessage(message)
        return "Success"
    }

}