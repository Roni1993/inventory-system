package com.github.roni1993.store.service

import com.github.roni1993.store.model.event.DeliveryEventDto
import com.github.roni1993.store.model.mapper.DeliveryMapper
import com.github.roni1993.store.repository.DeliveryEventRepository
import com.github.roni1993.store.repository.DeliveryViewRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    val mapper: DeliveryMapper,
    val eventRepo: DeliveryEventRepository,
    val viewRepo: DeliveryViewRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun process(event: DeliveryEventDto) {
        // Save new event
        val entity = mapper.toEntity(event)
        logger.info("entity: {}", entity)
        eventRepo.save(entity)
        
        // return last occurred event to fix out of order events.
        // in more complex situation the full history could be recalculated or multiple event streams could be combined
        val lastEvent = eventRepo.findFirstByDeliveryIdOrderByOccurredDesc(entity.deliveryId)
        viewRepo.save(mapper.toView(lastEvent));

    }
}