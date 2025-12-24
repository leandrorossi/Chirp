package com.leandrour.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform