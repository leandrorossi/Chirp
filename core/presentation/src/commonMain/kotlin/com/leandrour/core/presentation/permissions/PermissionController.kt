package com.leandrour.core.presentation.permissions

expect class PermissionController {
    suspend fun requestPermission(permission: Permission): PermissionState
}