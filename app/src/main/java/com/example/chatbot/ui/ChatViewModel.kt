package com.example.chatbot.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.Constant
import com.example.chatbot.data.MessageModel
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = Constant.apiKey
    )

    fun sendMessage(question: String) {
        Log.i("chat question", question)

        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) {
                            text(it.message)
                        }
                    }.toList()
                )
                messageList.add(MessageModel(question,"user"))

                val response = chat.sendMessage(question)
                messageList.add(MessageModel(response.text.toString(),"model"))

//                Log.i("response gemini", response.text.toString())
            } catch (e: Exception) {
                Log.e("chat error", "Error while sending message: ${e.message}")
            }
        }
    }

}