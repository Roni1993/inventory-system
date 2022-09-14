package com.github.roni1993.inventoryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StoreApplication

fun main(args: Array<String>) {
	runApplication<StoreApplication>(*args)
}
