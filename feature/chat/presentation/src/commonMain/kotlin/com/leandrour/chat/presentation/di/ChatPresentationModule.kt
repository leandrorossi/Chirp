package com.leandrour.chat.presentation.di

import com.leandrour.chat.presentation.chat_list_detail.ChatListDetailViewModel
import com.leandrour.chat.presentation.create_chat.CreateChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModelOf(::ChatListDetailViewModel)
    viewModelOf(::CreateChatViewModel)
}