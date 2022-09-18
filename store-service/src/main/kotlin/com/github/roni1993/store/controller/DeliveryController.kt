package com.github.roni1993.store.controller

import com.github.roni1993.store.model.dto.DeliveryDto
import com.github.roni1993.store.model.entity.QDeliveryView
import com.github.roni1993.store.model.mapper.DeliveryMapper
import com.github.roni1993.store.repository.DeliveryViewRepository
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.time.OffsetDateTime
import java.util.*

@Controller
class DeliveryController(val repo: DeliveryViewRepository, val mapper: DeliveryMapper) {

    @QueryMapping
    fun delivery(@Argument id: String): DeliveryDto {
        return repo.findById(id).map { mapper.toDto(it) }.get()
    }

    @QueryMapping
    fun deliveries(@Argument page: Pageable = Pageable.ofSize(20)): Page<DeliveryDto> {
        return repo.findAll(page).map(mapper::toDto)
    }

    @QueryMapping
    fun deliveriesPlannedTomorrow(@Argument page: Pageable = Pageable.ofSize(20)): Page<DeliveryDto> {
        val now = DateTime()
        val today: LocalDate = now.toLocalDate()
        val startOfTomorrow = today.plusDays(1).toDateTimeAtStartOfDay(now.zone)
        val endOfTomorrow = today.plusDays(2).toDateTimeAtStartOfDay(now.zone)
        val query = QDeliveryView.deliveryView.plannedDeliveryDate
            .between(startOfTomorrow.toDate(), endOfTomorrow.toDate())
        return repo.findAll(query, page).map(mapper::toDto)
    }

    @QueryMapping
    fun deliveredBetween(
        @Argument from: OffsetDateTime,
        @Argument to: OffsetDateTime,
        @Argument page: Pageable = Pageable.ofSize(20)
    ): Page<DeliveryDto> {
        val fromDate = Date.from(from.toInstant())
        val toDate = Date.from(to.toInstant())
        val query = QDeliveryView.deliveryView.actualDeliveryDate.between(fromDate, toDate)
        return repo.findAll(query, page).map(mapper::toDto)
    }
}