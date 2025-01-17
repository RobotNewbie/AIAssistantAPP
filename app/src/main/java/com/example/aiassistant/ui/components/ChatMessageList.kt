package com.example.aiassistant.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aiassistant.model.Message

@Composable
fun ChatMessageList(
    messages: List<Message>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) { message ->
            MessageItem(message = message)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun MessageItem(message: Message) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = message.content,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatMessageListPreview() {
    val previewMessages = listOf(
        Message(content = "你好！", isFromUser = true),
        Message(content = "你好！我是AI助手，很高兴为您服务。", isFromUser = false)
    )
    ChatMessageList(messages = previewMessages)
}
