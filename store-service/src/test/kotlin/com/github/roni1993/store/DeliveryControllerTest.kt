package com.github.roni1993.store

import com.github.roni1993.store.model.dto.Delivery
import com.github.roni1993.store.model.dto.Item
import com.github.roni1993.store.model.entity.DeliveryView
import com.github.roni1993.store.repository.DeliveryViewRepository
import io.github.serpro69.kfaker.Faker
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Sort
import org.springframework.graphql.test.tester.GraphQlTester
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@SpringBootTest
@AutoConfigureGraphQlTester
internal class DeliveryControllerTest {

    @Autowired
    private lateinit var graphQlTester: GraphQlTester

    @Autowired
    private lateinit var repo: DeliveryViewRepository

    @Test
    fun contextLoads() {
        assertNotNull(graphQlTester)
        assertNotNull(repo)
    }

    @Test
    fun `call delivery and return empty for not existing TransportID`() {
        // Given there are no entries in the DB
        // When a user requests a delivery with ID
        val response = graphQlTester.documentName("delivery")
            .variable("id", "T9999")
            .execute()

        // Then there should be no delivery returned
        response.path("delivery").valueIsNull()
        // and there should be no errors in the graphql response
        response.errors().verify()
    }

    @Test
    fun `call delivery for existing TransportID`() {
        // Given there are is an entry in the DB
        val delivery = createAndInsertDeliveryView("T00001", true, 1)
        createAndInsertDeliveryView("T00002", true, 1)
        createAndInsertDeliveryView("T00003", true, 1)

        // When a user requests a delivery with ID
        val response = graphQlTester.documentName("delivery")
            .variable("id", "T00001")
            .execute()

        // Then there should be a delivery returned
        response.path("delivery").hasValue()
        // and the delivery should have the correct values
        val dto = response.path("delivery").entity(Delivery::class.java).get()
        assertEquals(delivery.deliveryId, dto.id)
        assertEquals(delivery.plannedDeliveryDate, toDate(dto.plannedDeliveryDate?.toLocalDateTime()))
        assertEquals(delivery.actualDeliveryDate, toDate(dto.actualDeliveryDate?.toLocalDateTime()))
        assertEquals(delivery.status, dto.status?.key)
        assertEquals(delivery.category, dto.category?.key)
        // and there should be no errors in the graphql response
        response.errors().verify()
    }

    @Test
    fun `call deliveries with no parameters and a default page size is chosen`() {
        // Given there are 3 entries in the DB
        createAndInsertDeliveryView("T00001", true, 1)
        createAndInsertDeliveryView("T00002", true, 1)
        createAndInsertDeliveryView("T00003", true, 1)

        // When a user requests deliveries without passing a pageInput
        val response = graphQlTester.documentName("deliveriesNoParams").execute()

        // Then there should be a response returned
        response.path("deliveries").hasValue()
        // and there should be 3 deliveries
        assertEquals(response.path("deliveries.size").entity(Int::class.java).get(),20)
        assertEquals(response.path("deliveries.totalElements").entity(Int::class.java).get(),3)
        assertEquals(response.path("deliveries.totalPages").entity(Int::class.java).get(),1)
        response.path("deliveries.content[*]").entityList(Delivery::class.java).hasSize(3)
        // and there should be no errors in the graphql response
        response.errors().verify()
    }

    @Test
    fun `call deliveries with custom pagination & sorting`() {
        // Given there are 3 entries in the DB where one is planned before the other
        val delivery = createAndInsertDeliveryView("T00001", true, 0)
        createAndInsertDeliveryView("T00002", true, 1)
        createAndInsertDeliveryView("T00003", true, 2)

        // When a user requests deliveries with a page input with sorting on the deliveryID
        val response = graphQlTester
            .documentName("deliveries")
            .variable("page", 0)
            .variable("size", 2)
            .variable("property", "deliveryId")
            .variable("direction", Sort.Direction.ASC)
            .execute()

        // Then there should be a response returned
        response.path("deliveries").hasValue()
        // and there should be 3 deliveries
        val dto = response.path("deliveries.content[*]").entityList(Delivery::class.java).hasSize(2).get().first()
        // the first entry should be T00001 due to the sorting
        assertEquals(delivery.deliveryId, dto.id)
        assertEquals(delivery.plannedDeliveryDate, toDate(dto.plannedDeliveryDate?.toLocalDateTime()))
        assertEquals(delivery.actualDeliveryDate, toDate(dto.actualDeliveryDate?.toLocalDateTime()))
        assertEquals(delivery.status, dto.status?.key)
        assertEquals(delivery.category, dto.category?.key)
        // the page setup should be customized
        assertEquals(response.path("deliveries.size").entity(Int::class.java).get(),2)
        assertEquals(response.path("deliveries.totalElements").entity(Int::class.java).get(),3)
        assertEquals(response.path("deliveries.totalPages").entity(Int::class.java).get(),2)
        // and there should be no errors in the graphql response
        response.errors().verify()
    }

    @Test
    fun `call deliveriesPlannedTomorrow with 1 single delivery coming in`() {
        // Given there are 3 entries in the DB where one is planned before the other
        createAndInsertDeliveryView("T00001", true, 1)
        val tomorrow = createAndInsertDeliveryView("T00002", false, 0)
        createAndInsertDeliveryView("T00003", false, 2)

        // When a user requests tomorrows deliveries
        val response = graphQlTester.documentName("deliveriesPlannedTomorrow").execute()

        // Then there should be a response returned
        response.path("deliveriesPlannedTomorrow").hasValue()
        // and there should be 3 deliveries
        val dto = response.path("deliveriesPlannedTomorrow.content[*]").entityList(Delivery::class.java).hasSize(1).get().first()
        // the first entry should be T00001 due to the sorting
        assertEquals(tomorrow.deliveryId, dto.id)
        assertEquals(tomorrow.plannedDeliveryDate, toDate(dto.plannedDeliveryDate?.toLocalDateTime()))
        assertEquals(tomorrow.actualDeliveryDate, null)
        assertEquals(tomorrow.status, dto.status?.key)
        assertEquals(tomorrow.category, dto.category?.key)
        // the page should tell us that there is only one element
        assertEquals(response.path("deliveriesPlannedTomorrow.totalElements").entity(Int::class.java).get(),1)
        // and there should be no errors in the graphql response
        response.errors().verify()
    }

    @Test
    fun `call deliveredBetween to see all deliveries for a full week`() {
        // Given there are 3 entries in the DB where one is planned before the other
        createAndInsertDeliveryView("T00001", true, 0)
        val tomorrow = createAndInsertDeliveryView("T00002", true, 1)
        createAndInsertDeliveryView("T00003", false, 1)
        createAndInsertDeliveryView("T00004", true, 10)

        // When a user requests deliveries for a week
        val formatter = ISODateTimeFormat.dateTime()
        val response = graphQlTester.documentName("deliveredBetween")
            .variable("from", formatter.print(DateTime().minusDays(1)))
            .variable("to", formatter.print(DateTime().plusDays(6)))
            .execute()

        // Then there should be a response returned
        response.path("deliveredBetween").hasValue()
        // and there should be 2 deliveries
        val dto = response.path("deliveredBetween.content[*]").entityList(Delivery::class.java).hasSize(2)
        // the page should tell us that there are two elements as well
        assertEquals(response.path("deliveredBetween.totalElements").entity(Int::class.java).get(),2)
        // and there should be no errors in the graphql response
        response.errors().verify()
    }

    fun createAndInsertDeliveryView(id: String, delivered: Boolean, plannedInDays: Long): DeliveryView {
        val faker = Faker()
        val items = List(faker.random.nextInt(1, 10)) {
            Item(faker.appliance.equipment(), faker.random.nextInt(1, 99))
        }

        val planned = LocalDateTime.now().plusDays(plannedInDays)
        val actual = planned.plusDays(1)


        val delivery = DeliveryView(
            id,
            toDate(planned),
            if (delivered) toDate(actual) else null,
            faker.random.randomValue(listOf("truck", "parcel")),
            if (delivered) "delivered" else faker.random.randomValue(listOf("planned", "in-progress", "unknown")),
            items
        )
        println(delivery)
        repo.save(delivery)
        return delivery
    }

    @BeforeEach
    fun delete() {
        repo.deleteAll()
    }

    fun toDate(local: LocalDateTime?): Date {
        return Date.from(local?.toInstant(ZoneOffset.UTC))
    }
}