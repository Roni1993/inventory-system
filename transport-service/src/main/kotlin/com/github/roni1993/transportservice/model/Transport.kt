package com.github.roni1993.transportservice.model

import java.util.*

data class Transport(
    val id: String,
    val plannedDeliveryDate: Date,
    val actualDeliveryDate: Date,
    val category: String,
    val items: List<Item>,
    val status: String
)
