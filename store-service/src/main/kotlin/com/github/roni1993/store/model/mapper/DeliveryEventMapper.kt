package com.github.roni1993.store.model.mapper

import com.github.roni1993.store.model.dto.DeliveryDto
import com.github.roni1993.store.model.entity.DeliveryEvent
import com.github.roni1993.store.model.entity.DeliveryView
import com.github.roni1993.store.model.event.DeliveryEventDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

@Mapper(componentModel = "spring")
abstract class DeliveryEventMapper {
    @Mapping(source = "id", target = "deliveryId")
    abstract fun toEntity(event: DeliveryEventDto): DeliveryEvent
    abstract fun toView(entity: DeliveryEvent): DeliveryView
    abstract fun toDto(entity: DeliveryView): DeliveryDto

    fun toOffsetDate(date: Date): OffsetDateTime {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
    }
}