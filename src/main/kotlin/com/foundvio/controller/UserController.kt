package com.foundvio.controller

import com.foundvio.clouddb.model.User
import com.foundvio.service.KafkaProducer
import com.foundvio.service.UserService
import org.springframework.web.bind.annotation.*


@RestController
class UserController(
    val userService: UserService,
    val producer: KafkaProducer
) {

    @GetMapping
    fun index() = "Foundvio"

    @PostMapping
    fun addUser(@RequestBody user: User): String{
        userService.upsertUser(user)
        return "Success"
    }


    @GetMapping("/test")
    fun sendMessageToKafkaTopic(@RequestParam message: String): String {
        this.producer.sendMessage(message)
        return "Success"
    }

}