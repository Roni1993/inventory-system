package com.github.roni1993.store.subscriber

import com.github.roni1993.store.model.DeliveryEventDto
import com.github.roni1993.store.service.DeliveryService
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service

@Service
class DeliveryEventSubscriber(val service: DeliveryService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @JmsListener(destination = "\${delivery.queueName}")
    fun handle(event: DeliveryEventDto) {
        logger.info("event: {}", event)
        service.process(event)
    }
}