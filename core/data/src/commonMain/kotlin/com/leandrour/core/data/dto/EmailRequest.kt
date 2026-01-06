package com.leandrour.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmailRequest(
    val email: String
)
