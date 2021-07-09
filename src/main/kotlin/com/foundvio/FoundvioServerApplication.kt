package com.foundvio

import com.foundvio.clouddb.model.CredentialParserInputStream
import com.foundvio.validation.HuaweiAuthHandlerInterceptor
import com.huawei.agconnect.server.commons.AGCClient
import com.huawei.agconnect.server.commons.AGCParameter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ResourceLoader
import org.springframework.web.servlet.handler.MappedInterceptor

@SpringBootApplication

class FoundvioServerApplication(
    private val resourceLoader: ResourceLoader
) {

    init {
        initAGCClient()
    }

    private fun initAGCClient() {
        val credentialResource = resourceLoader
            .getResource("classpath:agc-apiclient-662148821073020224-6980207906738382842.json")

        AGCClient.initialize(
            AGCParameter.builder()
                .setCredential(CredentialParserInputStream.toCredential(credentialResource.inputStream))
                .build()
        )
    }

    @Bean
    fun getMappedInterceptor(interceptor: HuaweiAuthHandlerInterceptor): MappedInterceptor {
        return MappedInterceptor(arrayOf("/**"), interceptor)
    }


}

fun main(args: Array<String>) {
    runApplication<FoundvioServerApplication>(*args)
}
