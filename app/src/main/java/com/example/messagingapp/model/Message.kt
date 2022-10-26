package com.example.messagingapp.model

import androidx.annotation.DrawableRes
import java.util.Calendar

data class Message(
    val id: String,
    val direction: MessageDirection,
    val dateTime: Calendar,
    val sender: String,
    val message: String? = null,
    @DrawableRes val image: Int? = null,
    val altText: String? = null,
)

enum class MessageDirection {
    RECEIVED,
    SENT
}