package com.github.roni1993.store.model

import java.util.*

data class Event<T>(val eventId: UUID, val created: Date, val occurred: Date, val payload: T)
