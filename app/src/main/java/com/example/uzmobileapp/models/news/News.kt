package com.example.uzmobileapp.models.news

import java.io.Serializable

class News : Serializable {
    var title: String? = null
    var description: String? = null
    var image: String? = null

    constructor()

    constructor(title: String?, description: String?, image: String?) {
        this.title = title
        this.description = description
        this.image = image
    }

}

