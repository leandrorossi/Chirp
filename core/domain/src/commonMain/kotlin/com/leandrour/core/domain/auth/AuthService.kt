package com.leandrour.core.domain.auth

import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Remote>
}