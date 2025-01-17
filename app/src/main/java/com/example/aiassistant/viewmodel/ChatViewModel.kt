package com.example.aiassistant.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiassistant.model.ChatMessage
import com.example.aiassistant.model.Message
import com.example.aiassistant.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository,
    context: Context
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            
            // 添加用户消息
            val userMessage = Message(content = content, isFromUser = true)
            _messages.value = _messages.value + userMessage

            // 构建API请求消息历史
            val chatMessages = _messages.value.map { 
                ChatMessage(
                    role = if (it.isFromUser) "user" else "assistant",
                    content = it.content
                )
            }

            // 调用API
            repository.sendMessage(chatMessages).onSuccess { response ->
                val assistantMessage = Message(content = response, isFromUser = false)
                _messages.value = _messages.value + assistantMessage
            }.onFailure { error ->
                val errorMessage = Message(
                    content = "错误：${error.message ?: "未知错误"}",
                    isFromUser = false
                )
                _messages.value = _messages.value + errorMessage
            }

            _isLoading.value = false
        }
    }

    fun clearMessages() {
        _messages.value = emptyList()
    }
}