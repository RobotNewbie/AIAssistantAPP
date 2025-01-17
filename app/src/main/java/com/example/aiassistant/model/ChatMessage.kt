package com.example.aiassistant.model

data class ChatMessage(
    val role: String,
    val content: String
)

data class ChatRequest(
    val model: String = "qwen-plus",
    val messages: List<ChatMessage>,
    val result_format: String = "message"
)

data class ChatResponse(
    val output: Output,
    val usage: Usage
)

data class Output(
    val choices: List<Choice>
)

data class Choice(
    val message: ChatMessage,
    val finish_reason: String
)

data class Usage(
    val total_tokens: Int,
    val input_tokens: Int,
    val output_tokens: Int
) 