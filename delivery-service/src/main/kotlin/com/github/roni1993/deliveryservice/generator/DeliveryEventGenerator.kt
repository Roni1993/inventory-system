package com.github.roni1993.deliveryservice.generator

import com.github.roni1993.deliveryservice.model.DeliveryEventDto
import com.github.roni1993.deliveryservice.model.Item
import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.ThreadLocalRandom


@Service
class DeliveryEventGenerator {
    private val faker = Faker()
    val category = listOf("truck", "parcel")
    val status = listOf("planned", "in-progress", "delivered", "unknown")

    fun generateEvent(): DeliveryEventDto {
        val items = List(faker.random.nextInt(1, 10)) {
            Item(faker.appliance.equipment(), faker.random.nextInt(1, 99))
        }

        // user-actions that occurred in the past
        val now = LocalDateTime.now()
        val occurred = between(now.minusHours(3), now)

        // no transports earlier than 2 days in advance & upto 21 days in advance
        val startPlanned = now.plusDays(1)
        val endPlanned = now.plusDays(21)
        val planned = between(startPlanned, endPlanned)

        // logistics are unpredictable
        val actual = between(planned.minusDays(1), planned.plusDays(7))

        return DeliveryEventDto(
            UUID.randomUUID(),
            Date(),
            toDate(occurred),
            "TR%05d".format(faker.random.nextInt(1, 10)),
            toDate(planned),
            toDate(actual),
            faker.random.randomValue(category),
            items,
            faker.random.randomValue(status)
        )
    }

    fun between(startInclusive: LocalDateTime, endExclusive: LocalDateTime): LocalDateTime {
        val randomTime = ThreadLocalRandom.current().nextLong(
            startInclusive.toEpochSecond(ZoneOffset.UTC), endExclusive.toEpochSecond(ZoneOffset.UTC))
        return LocalDateTime.ofEpochSecond(randomTime,0, ZoneOffset.UTC)
    }

    fun toDate(local: LocalDateTime): Date {
        return Date.from(local.toInstant(ZoneOffset.UTC))
    }
}