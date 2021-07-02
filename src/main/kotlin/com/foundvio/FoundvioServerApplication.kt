package com.foundvio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class FoundvioServerApplication

fun main(args: Array<String>) {
    runApplication<FoundvioServerApplication>(*args)
}
