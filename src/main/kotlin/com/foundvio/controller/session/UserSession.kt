package com.foundvio.controller.session

import com.foundvio.clouddb.model.User
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

/**
 * Store in server session
 */
@Component
@Scope("session", proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserSession {
    var user: User? = null
}