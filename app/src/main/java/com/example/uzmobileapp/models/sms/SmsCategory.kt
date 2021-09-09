package com.example.uzmobileapp.models.sms

import com.example.uzmobileapp.models.internet.Internet
import java.io.Serializable

data class SmsCategory(
    val name: String,
    val positon: Int
) : Serializable
