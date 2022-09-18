package com.github.roni1993.store.model.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.Order
import org.springframework.data.domain.Sort.by

// Spring Graphql is not able to construct the spring-native Pageable object. therefore I created a dto
data class PageInput(val page: Int = 0, val size: Int = 20, val sort: List<OrderInput>? = null) {
    fun toPageable() : Pageable {
        return if (null == sort) PageRequest.of(page, size)
        else PageRequest.of(page, size, by(sort.map { it.toOrder() }))
    }
}

data class OrderInput(val property: String, val direction: Direction) {
    fun toOrder() : Order {
        return Order(direction, property)
    }
}
