package com.github.roni1993.store.model

import java.util.*

data class Event<T>(var eventId: UUID?, var created: Date?, var occurred: Date?, var payload: T?) {
    // Jackson requires a default constructor for this the event
    constructor() : this(null, null, null, null)
}
