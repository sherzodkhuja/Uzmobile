package com.example.uzmobileapp.models

import java.io.Serializable

class Xizmatlar : Serializable {
    var code: String? = null
    var cost: String? = null
    var description: String? = null
    var name: String? = null
    var url: String? = null

    constructor()

    constructor(code: String?, cost: String?, description: String?, name: String?, url: String?) {
        this.code = code
        this.cost = cost
        this.description = description
        this.name = name
        this.url = url
    }

}

