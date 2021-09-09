package com.example.uzmobileapp.models

import java.io.Serializable

class Ussd : Serializable {
    var code: String? = null
    var name: String? = null

    constructor(code: String?, name: String?) {
        this.code = code
        this.name = name
    }

    constructor()

}

