package com.example.aiassistant.network

import com.example.aiassistant.model.ChatRequest
import com.example.aiassistant.model.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TongYiApi {
    @POST("compatible-mode/v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") apiKey: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
} 