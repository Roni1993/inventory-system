package com.github.roni1993.transportservice.publisher

import com.github.roni1993.transportservice.generator.TransportEventGenerator
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TransportEventPublisher(val generator: TransportEventGenerator) {
    private val logger = LoggerFactory.getLogger(javaClass)
    var enabled = true

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    fun publishEvent() {
        if (enabled) {
            logger.info("event: {}", generator.generateEvent())
        }
    }
}