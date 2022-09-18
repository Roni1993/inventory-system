package com.github.roni1993.store.model.event

import com.github.roni1993.store.model.dto.ItemDto
import java.util.*

data class DeliveryEventDto(
    var eventId: UUID?,
    var created: Date?,
    var occurred: Date?,
    var id: String?,
    var plannedDeliveryDate: Date?,
    var actualDeliveryDate: Date?,
    var category: String?,
    var items: List<ItemDto>?,
    var status: String?
) {
    constructor() : this(null, null, null, null, null, null, null, null, null)
}
