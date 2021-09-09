package com.example.uzmobileapp.models

import java.io.Serializable

class Tariflar : Serializable {

    var code: String? = null
    var description: String? = null
    var mb: String? = null
    var minute_inside: String? = null
    var minute_uzb: String? = null
    var monthly_cost: String? = null
    var name: String? = null
    var sms: String? = null
    var url: String? = null


    constructor(
        description: String?,
        mb: String?,
        minute_inside: String?,
        minute_uzb: String?,
        monthly_cost: String?,
        name: String?,
        sms: String?,
        url: String?
    ) {
        this.description = description
        this.mb = mb
        this.minute_inside = minute_inside
        this.minute_uzb = minute_uzb
        this.monthly_cost = monthly_cost
        this.name = name
        this.sms = sms
        this.url = url
    }

    constructor()
}


