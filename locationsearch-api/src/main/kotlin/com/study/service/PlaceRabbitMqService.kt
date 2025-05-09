package com.study.service

import com.study.dto.PlaceData
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class PlaceRabbitMqService(
    private val rabbitTemplate: RabbitTemplate
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${rabbitmq.exchange}")
    lateinit var exchangeName: String

    @Value("\${rabbitmq.routing-key}")
    lateinit var routingKey: String

    fun sendMessage(data: PlaceData) {
        log.debug("Sending message to RabbitMQ: {}", data)
        Mono.fromCallable {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, data)
        }
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe()
    }
}
