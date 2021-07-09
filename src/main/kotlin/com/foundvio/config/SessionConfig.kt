package com.foundvio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.MapSessionRepository
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import java.util.concurrent.ConcurrentHashMap

@Configuration
@EnableSpringHttpSession
class SessionConfig : AbstractHttpSessionApplicationInitializer() {

    /**
     * In-memory session
     *
     * This should not be used in production since the session does not expire
     */
    @Bean
    fun sessionRepository() = MapSessionRepository(ConcurrentHashMap())

}