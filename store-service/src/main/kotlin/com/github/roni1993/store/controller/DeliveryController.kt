package com.github.roni1993.store.controller

import com.github.roni1993.store.entity.DeliveryView
import com.github.roni1993.store.entity.QDeliveryView
import com.github.roni1993.store.repository.DeliveryViewRepository
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class DeliveryController(val repo: DeliveryViewRepository) {

    @QueryMapping
    fun delivery(@Argument id: String): DeliveryView {
        return repo.findById(id).get()
    }

    @QueryMapping
    fun deliveries(@Argument page: Pageable = Pageable.ofSize(20)): Page<DeliveryView> {
        return repo.findAll(page)
    }

    @QueryMapping
    fun deliveriesPlannedTomorrow(@Argument page: Pageable = Pageable.ofSize(20)): Page<DeliveryView> {
        val now = DateTime()
        val today: LocalDate = now.toLocalDate()
        val startOfTomorrow = today.plusDays(1).toDateTimeAtStartOfDay(now.zone)
        val endOfTomorrow = today.plusDays(2).toDateTimeAtStartOfDay(now.zone)
        val query = QDeliveryView.deliveryView.plannedDeliveryDate
            .between(startOfTomorrow.toDate(), endOfTomorrow.toDate())
        return repo.findAll(query, page)
    }

    @QueryMapping
    fun deliveredBetween(
        @Argument from: Date,
        @Argument to: Date,
        @Argument page: Pageable = Pageable.ofSize(20)
    ): Page<DeliveryView> {
        val query = QDeliveryView.deliveryView.actualDeliveryDate.between(from, to)
        return repo.findAll(query, page)
    }
}