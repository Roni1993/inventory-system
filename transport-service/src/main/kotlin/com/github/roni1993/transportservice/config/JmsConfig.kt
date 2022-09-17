package com.github.roni1993.transportservice.config

import com.github.roni1993.transportservice.model.Event
import com.github.roni1993.transportservice.model.Transport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType

@Configuration
class JmsConfig {
    @Bean
    fun jacksonJmsMessageConverter(): MessageConverter? {
        val converter = MappingJackson2MessageConverter()
        converter.setTargetType(MessageType.TEXT)
        converter.setTypeIdPropertyName("_type")

        // Set up a map to convert our friendly message types to Java classes.
        converter.setTypeIdMappings(
            hashMapOf(
                "event" to Event::class.java,
                "transport" to Transport::class.java
            )
        )
        return converter
    }
}