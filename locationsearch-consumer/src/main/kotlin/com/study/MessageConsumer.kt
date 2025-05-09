package com.study

import com.study.dto.PlaceData
import com.study.model.PlaceDataRaw
import com.study.repository.PlaceDataRawRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MessageConsumer (
    private val placeDataRawRepository: PlaceDataRawRepository
){
    val log = LoggerFactory.getLogger(MessageConsumer::class.java)

    @Value("\${rabbitmq.queue}")
    lateinit var queueName: String

    @RabbitListener(queues = ["\${rabbitmq.queue}"])
    fun receiveMessage(data: PlaceData) {
        val placeDataRaw = PlaceDataRaw(keyword = data.keyword)
        placeDataRawRepository.save(placeDataRaw)
        log.debug("Received message from $queueName: ${data.keyword}")
    }
}
