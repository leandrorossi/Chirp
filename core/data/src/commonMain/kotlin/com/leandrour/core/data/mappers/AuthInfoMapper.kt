package com.leandrour.core.data.mappers

import com.leandrour.core.data.dto.AuthInfoSerializable
import com.leandrour.core.data.dto.UserSerializable
import com.leandrour.core.domain.auth.AuthInfo
import com.leandrour.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain()
    )
}

fun UserSerializable.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}

fun AuthInfo.toSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toSerializable()
    )
}

fun User.toSerializable(): UserSerializable {
    return UserSerializable(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}
