package com.github.roni1993.store.model.mapper

import com.github.roni1993.store.model.dto.DeliveryCategory
import com.github.roni1993.store.model.dto.Delivery
import com.github.roni1993.store.model.dto.DeliveryStatus
import com.github.roni1993.store.model.entity.DeliveryEvent
import com.github.roni1993.store.model.entity.DeliveryView
import com.github.roni1993.store.model.event.DeliveryEventDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

@Mapper(componentModel = "spring")
abstract class DeliveryMapper {
    @Mapping(source = "id", target = "deliveryId")
    abstract fun toEntity(event: DeliveryEventDto): DeliveryEvent
    abstract fun toView(entity: DeliveryEvent): DeliveryView
    @Mapping(source = "deliveryId", target = "id")
    abstract fun toDto(entity: DeliveryView): Delivery

    fun toCategory(category: String): DeliveryCategory {
        return DeliveryCategory.values().find { enum -> enum.key == category  }!!
    }
    fun toStatus(status: String): DeliveryStatus {
        return DeliveryStatus.values().find { enum -> enum.key == status  }!!
    }

    fun toOffsetDate(date: Date): OffsetDateTime {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
    }
}