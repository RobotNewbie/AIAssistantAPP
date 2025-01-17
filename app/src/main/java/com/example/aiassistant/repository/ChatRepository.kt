package com.example.aiassistant.repository

import com.example.aiassistant.model.ChatMessage
import com.example.aiassistant.model.ChatRequest
import com.example.aiassistant.network.TongYiApi
import androidx.annotation.Keep
import android.content.Context
import java.util.Properties

class ChatRepository(
    private val api: TongYiApi,
    private val context: Context
) {
    private val apiKey = try {
        val properties = Properties()
        context.assets.open("config.properties").use { 
            properties.load(it)
        }
        properties.getProperty("api_key") ?: throw IllegalStateException("API Key not found in config.properties")
    } catch (e: Exception) {
        throw IllegalStateException("Failed to load API Key: ${e.message}")
    }

    suspend fun sendMessage(messages: List<ChatMessage>): Result<String> = try {
        val systemMessage = ChatMessage(
            role = "system",
            content = "You are a helpful assistant."
        )
        val allMessages = listOf(systemMessage) + messages
        
        val request = ChatRequest(
            model = "qwen-plus",
            messages = allMessages
        )
        
        val response = api.chat("Bearer $apiKey", request)
        if (response.isSuccessful) {
            val content = response.body()?.choices?.firstOrNull()?.message?.content
            if (content != null) {
                Result.success(content)
            } else {
                Result.failure(Exception("响应内容为空"))
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API调用失败: ${response.code()}, $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
} 