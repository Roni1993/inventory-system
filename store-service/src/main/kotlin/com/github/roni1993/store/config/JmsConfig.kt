package com.github.roni1993.store.config

import com.github.roni1993.store.model.event.DeliveryEventDto
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

        val types = hashMapOf(
            "deliveryEvent" to DeliveryEventDto::class.java,
        ) as Map<String, Class<*>>
        converter.setTypeIdMappings(types)
        return converter
    }
}