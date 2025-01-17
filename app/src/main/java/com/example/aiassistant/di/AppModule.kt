package com.example.aiassistant.di

import android.content.Context
import com.example.aiassistant.network.TongYiApi
import com.example.aiassistant.repository.ChatRepository
import com.example.aiassistant.viewmodel.ChatViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TongYiApi::class.java)
    }
    
    single { ChatRepository(get()) }
    
    viewModel { (context: Context) -> 
        ChatViewModel(get(), context) 
    }
} 