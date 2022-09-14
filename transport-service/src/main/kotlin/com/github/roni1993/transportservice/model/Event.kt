package com.github.roni1993.transportservice.model

import java.util.*

data class Event<T>(val eventId: UUID, val created: Date, val occured: Date, val payload: T)
