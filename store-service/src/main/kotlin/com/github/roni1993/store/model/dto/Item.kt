package com.github.roni1993.store.model.dto

data class Item(var name: String?, var quantity: Int?){
    constructor() : this(null,null)
}
