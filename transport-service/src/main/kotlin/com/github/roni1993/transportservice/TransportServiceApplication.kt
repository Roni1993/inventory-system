package com.github.roni1993.transportservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TransportServiceApplication

fun main(args: Array<String>) {
	runApplication<TransportServiceApplication>(*args)
}
