package com.github.roni1993.store.service

import com.github.roni1993.store.entity.DeliveryEvent
import com.github.roni1993.store.entity.DeliveryView
import com.github.roni1993.store.model.DeliveryEventDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface DeliveryEventMapper {
    @Mapping(source = "id", target = "deliveryId")
    fun toEntity(event: DeliveryEventDto): DeliveryEvent
    fun toView(entity: DeliveryEvent): DeliveryView
}