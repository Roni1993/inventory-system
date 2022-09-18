package com.github.roni1993.store.model.dto

data class ItemDto(var name: String?, var quantity: Int?){
    constructor() : this(null,null)
}
