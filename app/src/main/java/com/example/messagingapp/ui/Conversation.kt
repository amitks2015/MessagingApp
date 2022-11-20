package com.example.messagingapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.messagingapp.model.ConversationState

@Composable
fun Conversation(
    modifier: Modifier = Modifier,
    conversationState: ConversationState
) {
    Column(modifier = modifier) {
        Header(modifier = Modifier.fillMaxWidth()) {
            // on close
        }
        Messages(
            modifier = Modifier.weight(1f),
            messages = conversationState.messages
        )
    }
}