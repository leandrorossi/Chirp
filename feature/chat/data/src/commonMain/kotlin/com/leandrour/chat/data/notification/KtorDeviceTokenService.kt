package com.leandrour.chat.data.notification

import com.leandrour.chat.data.dto.RegisterDeviceTokenDto
import com.leandrour.chat.domain.notification.DeviceTokenService
import com.leandrour.core.data.networking.delete
import com.leandrour.core.data.networking.post
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class KtorDeviceTokenService(
    private val httpClient: HttpClient
) : DeviceTokenService {

    override suspend fun registerToken(
        token: String,
        platform: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/notification/register",
            body = RegisterDeviceTokenDto(
                token = token,
                platform = platform
            )
        )
    }

    override suspend fun unregisterToken(token: String): EmptyResult<DataError.Remote> {
        return httpClient.delete(
            route = "/notification/$token"
        )
    }
}