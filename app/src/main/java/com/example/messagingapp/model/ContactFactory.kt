package com.example.messagingapp.model

object ContactFactory {
    fun makeContacts() = listOf(
        Contact("Joe"),
        Contact("Ellie"),
        Contact("Anna"),
        Contact("Rachel"),
        Contact("Ross"),
        Contact("Mark"),
        Contact("Jake")
    )
}