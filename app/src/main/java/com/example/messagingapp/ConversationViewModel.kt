package com.example.messagingapp

import androidx.lifecycle.ViewModel
import com.example.messagingapp.model.ConversationEvent
import com.example.messagingapp.model.ConversationState
import com.example.messagingapp.model.Message
import com.example.messagingapp.model.MessageDirection
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class ConversationViewModel: ViewModel() {

    val uiState = MutableStateFlow(ConversationState())

    fun handleConversationEvent(event: ConversationEvent) {
        when(event) {
            is ConversationEvent.SendEvent -> {
                val message = event.message
                uiState.value = uiState.value.copy(
                    messages = uiState.value.messages.toMutableList().apply{
                        add(buildMessage(message))
                    }.toList()
                )
            }
            is ConversationEvent.UnSendEvent -> {
                val id = event.id
                uiState.value = uiState.value.copy(
                    messages = removeMessage(id)
                )
            }
            is ConversationEvent.SelectMessage -> {
                val id = event.id
                uiState.value = uiState.value.copy(
                    selectedMessage = uiState.value.messages.firstOrNull {
                        it.id == id
                    }
                )
            }
            is ConversationEvent.UnSelectMessage -> {
                uiState.value = uiState.value.copy(
                    selectedMessage = null
                )
            }
        }
    }

    private fun buildMessage(message: String) =
        Message(
            uiState.value.messages.count().toString(),
            MessageDirection.SENT,
            Calendar.getInstance(),
            "me",
            message
        )

    private fun removeMessage(id: String): List<Message> =
        uiState.value.messages.toMutableList().apply {
            removeAt(
                uiState.value.messages.indexOfFirst {
                    it.id == id
                }
            )
        }
}
