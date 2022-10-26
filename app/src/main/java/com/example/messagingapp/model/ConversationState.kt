package com.example.messagingapp.model

data class ConversationState(
    val messages: List<Message> = MessageFactory.makeMessages(),
    val contacts: List<Contact> = ContactFactory.makeContacts(),
    val selectedMessage: Message? = null,
)