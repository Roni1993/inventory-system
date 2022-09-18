package com.github.roni1993.store.model.dto

import java.time.OffsetDateTime

data class DeliveryDto(
    var id: String?,
    var plannedDeliveryDate: OffsetDateTime?,
    var actualDeliveryDate: OffsetDateTime?,
    var category: DeliveryCategory?,
    var status: DeliveryStatus?,
    var items: List<ItemDto>?
) {
    constructor() : this(null, null, null, null, null, null)
}

enum class DeliveryCategory(val key: String) {
    TRUCK("truck"),
    PARCEL("parcel")
}

enum class DeliveryStatus(val key: String) {
    PLANNED("planned"),
    IN_PROGRESS("in-progress"),
    DELIVERED("delivered"),
    UNKNOWN("unknown")
}
