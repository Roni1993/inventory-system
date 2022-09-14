package com.github.roni1993.transportservice.generator

import com.github.roni1993.transportservice.model.Event
import com.github.roni1993.transportservice.model.Item
import com.github.roni1993.transportservice.model.Transport
import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Service
import java.util.*


@Service
class TransportEventGenerator {
    private val faker = Faker()
    val category = listOf("truck", "parcel")
    val status = listOf("planned", "in-progress", "delivered", "unknown")

    fun generateEvent(): Event<Transport> {
        val items = List(faker.random.nextInt(1, 10)) {
            Item(faker.appliance.equipment(), faker.random.nextInt(1, 99))
        }

        val transport = Transport(
            "TR%05d".format(faker.random.nextInt(1, 10000)),
            Date(),
            Date(),
            faker.random.randomValue(category),
            items,
            faker.random.randomValue(status)
        )

        val event = Event(
            UUID.randomUUID(),
            Date(),
            Date(),
            transport
        )
        return event
    }
}