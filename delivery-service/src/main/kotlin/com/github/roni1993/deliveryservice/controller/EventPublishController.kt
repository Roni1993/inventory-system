package com.github.roni1993.deliveryservice.controller

import com.github.roni1993.deliveryservice.publisher.DeliveryEventPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EventPublishController(private val publisher: DeliveryEventPublisher) {

    @PostMapping("enable")
    fun enable() {
        publisher.enabled = true
        println("enabled!")
    }

    @PostMapping("disable")
    fun disable() {
        publisher.enabled = false
        println("disabled!")
    }

}