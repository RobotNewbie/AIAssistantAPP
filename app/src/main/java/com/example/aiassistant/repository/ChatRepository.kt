package com.example.aiassistant.repository

import com.example.aiassistant.model.ChatMessage
import com.example.aiassistant.model.ChatRequest
import com.example.aiassistant.network.TongYiApi

class ChatRepository(private val api: TongYiApi) {
    private val apiKey = "sk-c719c7b059e7444198c99b69489a81ae" // 替换为你的API Key

    suspend fun sendMessage(messages: List<ChatMessage>): Result<String> = try {
        val request = ChatRequest(
            messages = messages
        )
        
        val response = api.chat(apiKey, request)
        if (response.isSuccessful) {
            val content = response.body()?.output?.choices?.firstOrNull()?.message?.content
            if (content != null) {
                Result.success(content)
            } else {
                Result.failure(Exception("响应内容为空"))
            }
        } else {
            Result.failure(Exception("API调用失败: ${response.code()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
} 