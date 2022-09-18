package com.github.roni1993.deliveryservice.generator

import com.github.roni1993.deliveryservice.model.DeliveryEventDto
import com.github.roni1993.deliveryservice.model.Item
import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Service
import java.util.*


@Service
class DeliveryEventGenerator {
    private val faker = Faker()
    val category = listOf("truck", "parcel")
    val status = listOf("planned", "in-progress", "delivered", "unknown")

    fun generateEvent(): DeliveryEventDto {
        val items = List(faker.random.nextInt(1, 10)) {
            Item(faker.appliance.equipment(), faker.random.nextInt(1, 99))
        }

        val event = DeliveryEventDto(
            UUID.randomUUID(),
            Date(),
            Date(),
            "TR%05d".format(faker.random.nextInt(1, 10)),
            Date(),
            Date(),
            faker.random.randomValue(category),
            items,
            faker.random.randomValue(status)
        )
        return event
    }
}