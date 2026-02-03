package com.leandrour.chat.presentation.manage_chats

import androidx.lifecycle.ViewModel
import com.leandrour.chat.presentation.components.manage_chats.ManageChatAction
import com.leandrour.chat.presentation.components.manage_chats.ManageChatState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ManageChatViewModel: ViewModel() {

    private val eventChannel = Channel<ManageChatEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ManageChatState())
    val state = _state.asStateFlow()

    fun onAction(action: ManageChatAction) {
        when(action) {
            ManageChatAction.OnAddClick -> {}
            ManageChatAction.OnDismissDialog -> {}
            ManageChatAction.OnPrimaryActionClick -> {}
        }
    }
}