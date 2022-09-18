package com.github.roni1993.store.repository

import com.github.roni1993.store.model.entity.DeliveryView
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryViewRepository :
    PagingAndSortingRepository<DeliveryView, String>,
    QuerydslPredicateExecutor<DeliveryView>