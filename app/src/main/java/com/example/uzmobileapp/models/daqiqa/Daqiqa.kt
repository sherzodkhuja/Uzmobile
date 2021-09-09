package com.example.uzmobileapp.models.daqiqa

import java.io.Serializable

class Daqiqa : Serializable {

    var code: String? = null
    var expire_date: String? = null
    var minut: String? = null
    var name: String? = null
    var price: String? = null
    var type: String? = null

    constructor()
    constructor(
        code: String?,
        expire_date: String?,
        minut: String?,
        name: String?,
        price: String?,
        type: String?
    ) {
        this.code = code
        this.expire_date = expire_date
        this.minut = minut
        this.name = name
        this.price = price
        this.type = type
    }

}

