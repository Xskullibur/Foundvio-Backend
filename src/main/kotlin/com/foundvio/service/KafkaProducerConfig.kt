package com.foundvio.service

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate


@Configuration
class KafkaProducerConfig(
    val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaProducerConfig::class.java)
        private const val TOPIC = "k874dhrh-users"
    }

    fun sendMessage(message: String) {
        logger.info(String.format("#### -> Producing message -> %s", message))
        kafkaTemplate.send(TOPIC, message)
    }
}