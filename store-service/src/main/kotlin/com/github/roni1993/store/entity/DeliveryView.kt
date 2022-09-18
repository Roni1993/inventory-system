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
data class DeliveryView(
    @Id val deliveryId: String,
    val plannedDeliveryDate: Date,
    val actualDeliveryDate: Date?,
    val category: String,
    val status: String,
    @Type(type = "jsonb") @Column(columnDefinition = "jsonb") val items: List<Item>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as DeliveryView

        return deliveryId == other.deliveryId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $deliveryId , plannedDeliveryDate = $plannedDeliveryDate , " +
                "actualDeliveryDate = $actualDeliveryDate , category = $category , items = $items , status = $status )"
    }

}
