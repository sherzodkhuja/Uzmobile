package com.example.uzmobileapp.models

import java.io.Serializable

class Operator : Serializable {
    var description: String? = null
    var phone: String? = null

    constructor(description: String?, phone: String?) {
        this.description = description
        this.phone = phone
    }

    constructor()
}

