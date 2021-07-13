package com.foundvio.controller

import com.foundvio.clouddb.model.User
import com.foundvio.controller.request.SetupUserDetails
import com.foundvio.controller.session.UserSession
import com.foundvio.service.KafkaProducer
import com.foundvio.service.UserService
import com.foundvio.utils.Logging
import com.foundvio.utils.Response
import com.foundvio.utils.logger
import com.huawei.agconnect.server.auth.service.AGCAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class UserController(
    val userService: UserService,
    val producer: KafkaProducer
) : Logging{

    @Autowired
    lateinit var userSession: UserSession

    private val logger = logger()

    @GetMapping
    fun index() = Response.Success()

    @PostMapping("registerUser")
    fun registerUser(
        @RequestHeader("access-token") accessToken: String,
        @RequestBody userDetails: SetupUserDetails
    ): Response<Any> {
        //Validate user input
        val invalidMessages = userDetails.validate()
        if(invalidMessages.isNotEmpty()){
            return Response.Error(invalidMessages)
        }

        //Upsert user
        return try{
            val agcAuth = AGCAuth.getInstance()
            val accessTokenResult = agcAuth.verifyAccessToken(accessToken, true)

            val user = User().apply {
                this.id = accessTokenResult.sub
                this.phone = userDetails.phone
                this.familyName = userDetails.familyName
                this.givenName = userDetails.givenName
                this.isTracker = userDetails.isTracker
            }
            userService.upsertUser(user)
            Response.Success()
        }catch (e: Exception){
            Response.Error("Unable to register user")
        }
    }

    @GetMapping("userDetails")
    fun userDetails(): Response<Any>{
        val user = userSession.user
        return if(user != null){
            Response.Success(user)
        }else{
            Response.Error("Unable to find user")
        }
    }

    @PostMapping("getUserById")
    fun getUserById(@RequestBody userId: Long): Response<Any> {

        return try {

            val user = userService.queryUserById(userId)
            if (user != null) {
                Response.Success(user)
            }
            else {
                Response.Error("Not Registered")
            }
        }
        catch (e: Exception){
            Response.Error("Unable to query User")
        }
    }

    @GetMapping("/test")
    fun sendMessageToKafkaTopic(@RequestParam message: String): String {
        this.producer.sendMessage(message)
        return "Success"
    }

}