package com.github.roni1993.store.config

import com.github.roni1993.store.model.Event
import com.github.roni1993.store.model.Transport
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

        converter.setTypeIdMappings(
            hashMapOf(
                "event" to Event::class.java,
                "transport" to Transport::class.java
            )
        )
        return converter
    }
}