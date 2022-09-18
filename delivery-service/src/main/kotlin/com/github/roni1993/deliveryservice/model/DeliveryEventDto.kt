package com.github.roni1993.deliveryservice.model

import java.util.*

data class DeliveryEventDto(
    val eventId: UUID,
    val created: Date,
    val occurred: Date,
    val id: String,
    val plannedDeliveryDate: Date,
    val actualDeliveryDate: Date,
    val category: String,
    val items: List<Item>,
    val status: String
)
