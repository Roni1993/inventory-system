package com.github.roni1993.store

import com.github.roni1993.store.model.dto.Item
import com.github.roni1993.store.model.event.DeliveryEventDto
import com.github.roni1993.store.repository.DeliveryEventRepository
import com.github.roni1993.store.repository.DeliveryViewRepository
import com.github.roni1993.store.service.DeliveryService
import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@SpringBootTest
internal class DeliveryServiceTest {

    @Autowired
    private lateinit var viewRepo: DeliveryViewRepository

    @Autowired
    private lateinit var eventRepo: DeliveryEventRepository

    @Autowired
    private lateinit var service: DeliveryService

    @Test
    fun contextLoads() {
        assertNotNull(viewRepo)
        assertNotNull(eventRepo)
        assertNotNull(service)
    }

    @BeforeEach
    fun prepareRepos() {
        viewRepo.deleteAll()
        eventRepo.deleteAll()
    }

    @Test
    fun `process the first event for a DeliveryID`() {
        //Given we have an event that is supposed to be processed
        val eventId = UUID.randomUUID()
        val deliveryId = "T00001"
        val input = generateEvent(eventId,deliveryId, false, 1,2)
        // and we know that the repos are empty
        assertEquals(0, viewRepo.count())
        assertEquals(0, eventRepo.count())

        //When the event is received
        service.process(input)

        //Then we should see that the repos have one entry now
        assertEquals(1, viewRepo.count())
        assertEquals(1, eventRepo.count())
        // and the viewEntity matches our dto
        assertDeliveryExistsInView(deliveryId, input)
        // and the eventStore has our event
        assertEventExistsInStore(eventId, input)
    }

    @Test
    fun `process an event that is being replayed & ingested for a 2nd time`() {
        //Given we have an event that is supposed to be processed
        val eventId = UUID.randomUUID()
        val deliveryId = "T00001"
        val input = generateEvent(eventId,deliveryId, false, 1,2)
        // and we know that the repos are empty
        assertEquals(0, viewRepo.count())
        assertEquals(0, eventRepo.count())

        //When the event is received twice
        service.process(input)
        service.process(input)

        //Then we should see that both repos still only have one repo
        assertEquals(1, viewRepo.count())
        assertEquals(1, eventRepo.count())
        // and the viewEntity matches our dto
        assertDeliveryExistsInView(deliveryId, input)
        // and the eventStore has our event
        assertEventExistsInStore(eventId, input)
    }

    @Test
    fun `process events that are delivered out of order`() {
        //Given we have two events arrive us out of order for the same transport
        val eventId1 = UUID.randomUUID()
        val eventId2 = UUID.randomUUID()
        val deliveryId = "T00001"
        val delivered = generateEvent(eventId1, deliveryId, true, 1,2)
        val planned = generateEvent(eventId2, deliveryId, false, 2,1)
        // and we know that the repos are empty
        assertEquals(0, viewRepo.count())
        assertEquals(0, eventRepo.count())

        //When the event is received twice
        service.process(delivered)
        service.process(planned)

        //Then we should see that both the view has one transport but the event repo has 2 events
        assertEquals(1, viewRepo.count())
        assertEquals(2, eventRepo.count())
        // and the viewEntity matches our later event
        assertDeliveryExistsInView(deliveryId, delivered)
        // and the eventStore has both events
        assertEventExistsInStore(eventId1, delivered)
        assertEventExistsInStore(eventId2, planned)
    }

    @Test
    fun `process events fot two different deliveries`() {
        //Given we have two events arrive for each transport
        val eventId1 = UUID.randomUUID()
        val eventId2 = UUID.randomUUID()
        val eventId3 = UUID.randomUUID()
        val eventId4 = UUID.randomUUID()
        val deliveryId1 = "T00001"
        val deliveryId3 = "T00002"
        val planned1 = generateEvent(eventId1, deliveryId1, false, 2,2)
        val delivered1 = generateEvent(eventId2, deliveryId1, true, 1,2)
        val planned3 = generateEvent(eventId3, deliveryId3, false, 2,2)
        val delivered3 = generateEvent(eventId4, deliveryId3, true, 1,2)
        // and we know that the repos are empty
        assertEquals(0, viewRepo.count())
        assertEquals(0, eventRepo.count())

        //When the event is received twice
        service.process(delivered1)
        service.process(planned1)
        service.process(delivered3)
        service.process(planned3)

        //Then we should see that both the view has two transports but the event repo has 2 events for each transport
        assertEquals(2, viewRepo.count())
        assertEquals(4, eventRepo.count())
        // and the viewEntity matches our later event
        assertDeliveryExistsInView(deliveryId1, delivered1)
        assertDeliveryExistsInView(deliveryId3, delivered3)
        // and the eventStore has both events
        assertEventExistsInStore(eventId1, planned1)
        assertEventExistsInStore(eventId2, delivered1)
        assertEventExistsInStore(eventId3, planned3)
        assertEventExistsInStore(eventId4, delivered3)
    }

    private fun assertDeliveryExistsInView(deliveryId: String, event: DeliveryEventDto) {
        val viewEntity = viewRepo.findById(deliveryId).get()
        assertEquals(event.id, viewEntity.deliveryId)
        assertEquals(event.actualDeliveryDate, viewEntity.actualDeliveryDate)
        assertEquals(event.plannedDeliveryDate, viewEntity.plannedDeliveryDate)
        assertEquals(event.status, viewEntity.status)
        assertEquals(event.category, viewEntity.category)
        assertEquals(event.items, viewEntity.items)
    }

    private fun assertEventExistsInStore(eventId1: UUID, event: DeliveryEventDto) {
        val eventEntity = eventRepo.findById(eventId1).get()
        assertEquals(event.eventId, eventEntity.eventId)
        assertEquals(event.occurred, eventEntity.occurred)
        assertEquals(event.created, eventEntity.created)
        assertEquals(event.id, eventEntity.deliveryId)
        assertEquals(event.actualDeliveryDate, eventEntity.actualDeliveryDate)
        assertEquals(event.plannedDeliveryDate, eventEntity.plannedDeliveryDate)
        assertEquals(event.status, eventEntity.status)
        assertEquals(event.category, eventEntity.category)
        assertEquals(event.items, eventEntity.items)
    }


    fun generateEvent(eventId: UUID, id: String, delivered: Boolean, occurredHoursAgo: Long, plannedInDays: Long): DeliveryEventDto {
        val faker = Faker()
        val items = List(faker.random.nextInt(1, 10)) {
            Item(faker.appliance.equipment(), faker.random.nextInt(1, 99))
        }

        val now = LocalDateTime.now()
        val occurred = now.minusHours(occurredHoursAgo)
        val planned = now.plusDays(plannedInDays)
        val actual = planned.plusDays(1)

        return DeliveryEventDto(
            eventId,
            Date(),
            toDate(occurred),
            id,
            toDate(planned),
            if (delivered) toDate(actual) else null,
            faker.random.randomValue(listOf("truck", "parcel")),
            items,
            if(delivered) "delivered" else faker.random.randomValue(listOf("planned", "in-progress", "unknown"))
        )
    }

    fun toDate(local: LocalDateTime): Date {
        return Date.from(local.toInstant(ZoneOffset.UTC))
    }
}