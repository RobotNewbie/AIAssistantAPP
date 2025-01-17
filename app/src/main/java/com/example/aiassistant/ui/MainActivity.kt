package com.example.aiassistant.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.aiassistant.ui.components.ChatInput
import com.example.aiassistant.ui.components.ChatMessageList
import com.example.aiassistant.ui.theme.AIAssistantTheme
import com.example.aiassistant.viewmodel.ChatViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    private val viewModel: ChatViewModel by viewModel { 
        parametersOf(this) 
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIAssistantTheme {
                ChatScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI助手") },
                navigationIcon = {
                    IconButton(onClick = { /* 打开抽屉 */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "菜单")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.clearMessages() }) {
                        Icon(Icons.Default.Clear, contentDescription = "清除消息")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ChatMessageList(
                messages = messages,
                modifier = Modifier.weight(1f)
            )

            ChatInput(
                onSendMessage = { viewModel.sendMessage(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
} 