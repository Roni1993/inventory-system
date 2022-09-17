package com.github.roni1993.transportservice.publisher

import com.github.roni1993.transportservice.generator.TransportEventGenerator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


@Service
class TransportEventPublisher(
    val generator: TransportEventGenerator,
    val jmsTemplate: JmsTemplate,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    var enabled = true
    @Value("\${event.queueName}")
    lateinit var queueName: String

    @PostConstruct
    private fun customizeJmsTemplate() {
        // Update the jmsTemplate's connection factory to cache the connection
        val ccf = CachingConnectionFactory()
        ccf.targetConnectionFactory = jmsTemplate.connectionFactory
        jmsTemplate.connectionFactory = ccf

        // By default, Spring Integration uses Queues, but if you set this to true you
        // will send to a PubSub+ topic destination
        jmsTemplate.isPubSubDomain = false
    }


    @Scheduled(fixedRate = 10000)
    @Throws(Exception::class)
    fun sendEvent() {
        if (enabled) {
            val msg = generator.generateEvent()
            logger.info("event: {}", msg)
            jmsTemplate.convertAndSend(queueName, msg)
        }
    }

}