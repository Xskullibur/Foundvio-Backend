package com.foundvio.controller

import com.foundvio.clouddb.model.User
import com.foundvio.service.CloudDBService
import com.foundvio.service.KafkaProducerConfig
import com.huawei.agconnect.server.clouddb.exception.AGConnectCloudDBException
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener.LOGGER
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


@RestController
class UserController(
    val cloudDBService: CloudDBService,
    val producer: KafkaProducerConfig
) {

    @GetMapping
    fun index() = "Foundvio"

    @PostMapping
    fun addUser(@RequestBody user: User): String{
        upsertUser(user)
        return "Success"
    }

    fun upsertUser(user: User) {
        val mCloudDBZone = cloudDBService.mCloudDBZone
        try {
            val result: CompletableFuture<Int> = mCloudDBZone.executeUpsert(user)
            println(result.get())
        } catch (e: AGConnectCloudDBException) {
            LOGGER.warn("upsertBookInfo: " + e.message)
        } catch (e: ExecutionException) {
            LOGGER.warn("upsertBookInfo: " + e.message)
        } catch (e: InterruptedException) {
            LOGGER.warn("upsertBookInfo: " + e.message)
        }
    }

    @GetMapping("/test")
    fun sendMessageToKafkaTopic(@RequestParam message: String) {
        this.producer.sendMessage(message)
    }

}