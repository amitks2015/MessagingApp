package com.example.messagingapp.ui

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.messagingapp.ConversationViewModel
import com.example.messagingapp.R
import com.example.messagingapp.model.Message
import com.example.messagingapp.model.MessageDirection
import com.example.messagingapp.ui.theme.MessagingAppTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Messaging() {
    val viewModel: ConversationViewModel = viewModel()
    MessagingAppTheme {
        Conversation(
            modifier = Modifier.fillMaxSize(),
            conversationState = viewModel.uiState.collectAsState().value
        )
    }
}

@Composable
fun Messages(
    modifier: Modifier = Modifier,
    messages: List<Message>? = null
) {
    if(messages.isNullOrEmpty()) {
        EmptyConversation(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LazyColumn(
            modifier = modifier,
            state = rememberLazyListState(),
            contentPadding = PaddingValues(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(messages) { message ->
                Message(
                    modifier = Modifier.fillMaxWidth(),
                    message = message
                )
            }
        }
    }
}

@Composable
fun EmptyConversation(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.label_no_message))
    }
}

@Composable
fun Message(
    modifier: Modifier = Modifier,
    message: Message
) {
    val dataFormat = remember{ SimpleDateFormat("hh:mm", Locale.getDefault())}
    val parentModifier = if(message.direction == MessageDirection.SENT) {
        modifier.padding(end = 16.dp)
    } else {
        modifier.padding(start = 16.dp)
    }

    var displayTimeSent by remember { mutableStateOf(false) }

    Column(
        modifier = parentModifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    displayTimeSent = !displayTimeSent
                }
            )
        }
    ) {
        val alignment = if(message.direction == MessageDirection.SENT) {
            Alignment.End
        } else {
            Alignment.Start
        }
        val messageBackground = if(message.direction == MessageDirection.SENT) {
            Color.Cyan
        } else {
            Color.Blue
        }
        Box(
            modifier = Modifier
                .align(alignment)
                .background(
                    messageBackground.copy(alpha = 0.2f),
                    RoundedCornerShape(6.dp)
                )
                .padding(8.dp)
        ) {
            MessageBody(
                modifier = Modifier.align(Alignment.Center),
                message = message
            )
        }
        AnimatedVisibility(
            modifier = Modifier.align(alignment),
            visible = displayTimeSent
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = dataFormat.format(message.dateTime.time),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun MessageBody(
    modifier: Modifier = Modifier,
    message: Message
) {
    if(message.message != null) {
        Text(
            modifier = modifier,
            text = message.message
        )
    } else if(message.image != null) {
        Image(
            modifier = Modifier.size(120.dp),
            bitmap = BitmapFactory.decodeResource(
                LocalContext.current.resources,
                message.image
            ).asImageBitmap(),
            contentDescription =  message.altText
        )
    }
}

@Preview
@Composable
fun PreviewEmptyConversation() {
    EmptyConversation(modifier = Modifier.fillMaxSize())
}