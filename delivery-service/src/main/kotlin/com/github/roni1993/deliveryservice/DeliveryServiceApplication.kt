package com.github.roni1993.deliveryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DeliveryServiceApplication

fun main(args: Array<String>) {
	runApplication<DeliveryServiceApplication>(*args)
}
