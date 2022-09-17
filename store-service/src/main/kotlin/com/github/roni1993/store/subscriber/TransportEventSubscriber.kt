package com.github.roni1993.store.subscriber

import com.github.roni1993.store.model.Event
import com.github.roni1993.store.model.Transport
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service

@Service
class TransportEventSubscriber {
    private val logger = LoggerFactory.getLogger(javaClass)

    @JmsListener(destination = "\${transport.queueName}")
    fun handle(message: Event<Transport>) {
        logger.info("event: {}", message)
    }
}