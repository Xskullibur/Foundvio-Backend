package com.foundvio.validation

import com.foundvio.controller.session.UserSession
import com.foundvio.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HuaweiAuthHandlerInterceptor : HandlerInterceptor {
    @Autowired
    lateinit var userSession: UserSession
    @Autowired
    lateinit var userService: UserService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return true
        val accessToken = request.getHeader("access-token")
        if(userSession.user != null){
            //Already authenticated
            return true
        }
        return HuaweiAuthValidation.verifyTokenAndSaveSession(userSession, userService, accessToken)
    }
}