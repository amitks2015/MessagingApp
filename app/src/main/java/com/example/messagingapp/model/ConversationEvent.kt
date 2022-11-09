package com.example.messagingapp.model

sealed class ConversationEvent {
    data class SendEvent(val message: String): ConversationEvent()
    data class UnSendEvent(val id: String): ConversationEvent()
    data class SelectMessage(val id: String): ConversationEvent()
    object UnSelectMessage: ConversationEvent()
}
