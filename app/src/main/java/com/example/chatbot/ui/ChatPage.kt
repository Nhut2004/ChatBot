package com.example.chatbot.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatbot.data.MessageModel
import com.example.chatbot.ui.theme.ColorUserMessage
import com.example.chatbot.ui.theme.ColorModelMessage
@Composable
fun ChatPageApp(modifier: Modifier = Modifier) {
    val viewModel: ChatViewModel = viewModel()
    ChatPage(modifier = modifier, viewModel)

}


@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {

    Column(
        modifier = modifier
    ) {
        HeaderChatBot()
        MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
        MessageInputText(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList.reversed()) {
            MessageRow(messageModel = it)
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if(isModel) 8.dp else 70.dp,
                        end = if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if(isModel) ColorModelMessage else ColorUserMessage)
                    .padding(16.dp)
            ) {
                SelectionContainer{
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }
            }
        }

    }

}

@Composable
fun HeaderChatBot() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "My ChatBot",
            modifier = Modifier.padding(16.dp),
            fontSize = 22.sp,
            color = Color.White
        )
    }
}

@Composable
fun MessageInputText(onMessageSend: (String) -> Unit) {

    var inputText by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
            },
            modifier = Modifier.weight(1f)
        )
//        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                onMessageSend(inputText)
                inputText = ""
            },
            modifier = Modifier.size(55.dp)
        ) {
            Image(
                imageVector = Icons.Default.Send,
                contentDescription = "",

                )
        }

    }
}