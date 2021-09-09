package com.example.uzmobileapp.models.internet

import java.io.Serializable

class Internet : Serializable {
    var amount: String? = null
    var code: String? = null
    var expire_date: String? = null
    var name: String? = null
    var price: String? = null
    var url: String? = null

    constructor(
        amount: String?,
        code: String?,
        expire_date: String?,
        name: String?,
        price: String?,
        url: String?
    ) {
        this.amount = amount
        this.code = code
        this.expire_date = expire_date
        this.name = name
        this.price = price
        this.url = url
    }

    constructor()
}
