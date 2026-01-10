package com.leandrour.core.data.dto

data class ResetPasswordRequest(
    val newPassword: String,
    val token: String
)
