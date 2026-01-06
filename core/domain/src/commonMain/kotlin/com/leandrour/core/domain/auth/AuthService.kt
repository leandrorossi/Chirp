package com.leandrour.core.domain.auth

import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Remote>

    suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote>

    suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote>
}