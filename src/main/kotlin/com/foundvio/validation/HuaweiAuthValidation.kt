package com.foundvio.validation

import com.foundvio.controller.session.UserSession
import com.foundvio.service.UserService
import com.foundvio.utils.Logging
import com.foundvio.utils.logger
import com.huawei.agconnect.server.auth.exception.AGCAuthException
import com.huawei.agconnect.server.auth.service.AGCAuth
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@Component
object HuaweiAuthValidation : Logging {

    private val logger = logger()

    fun verifyTokenAndSaveSession(userSession: UserSession,
                                  userService: UserService,
                                  token: String?): Boolean {
        val agcAuth = AGCAuth.getInstance()
        if(token == null)throw InvalidAccessToken()

        try{
            val accessToken = agcAuth.verifyAccessToken(token, true)
            userSession.user = userService.queryUserById(accessToken.sub)
            return true
        }catch (e: AGCAuthException){
            logger.error("Invalid access token, error code: ${e.errorCode}")
            throw InvalidAccessToken()
        }
    }

    class InvalidAccessToken : RuntimeException()

    @ControllerAdvice
    class InvalidAccessTokenHandler {
        @ExceptionHandler(InvalidAccessToken::class)
        @ResponseBody
        fun handler(): Map<String, String>{
            return mapOf(
                "status" to "error",
                "message" to "invalid access token",
            )
        }
    }


}