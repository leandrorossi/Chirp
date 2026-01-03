package com.leandrour.core.data.auth

import com.leandrour.core.data.dto.RegisterRequest
import com.leandrour.core.data.networking.post
import com.leandrour.core.domain.auth.AuthService
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class KtorAuthServiceImpl(
    private val httpClient: HttpClient
) : AuthService {

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(
                email = email,
                username = username,
                password = password
            )
        )
    }
}