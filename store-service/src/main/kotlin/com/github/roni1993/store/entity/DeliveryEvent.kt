package com.github.roni1993.store.entity

import com.github.roni1993.store.model.Item
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class DeliveryEvent(
    @Id val eventId: UUID,
    val created: Date,
    val occurred: Date,
    val deliveryId: String,
    val plannedDeliveryDate: Date,
    val actualDeliveryDate: Date?,
    val category: String,
    @Type(type = "jsonb") @Column(columnDefinition = "jsonb") val items: List<Item>,
    val status: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as DeliveryEvent

        return eventId == other.eventId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $eventId )"
    }
}
