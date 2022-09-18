package com.github.roni1993.store.repository

import com.github.roni1993.store.model.entity.DeliveryEvent
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryEventRepository : CrudRepository<DeliveryEvent, UUID> {
    fun findAllByDeliveryIdOrderByOccurredAsc(deliveryId: String): List<DeliveryEvent>

    fun findFirstByDeliveryIdOrderByOccurredDesc(deliveryId: String): DeliveryEvent
}