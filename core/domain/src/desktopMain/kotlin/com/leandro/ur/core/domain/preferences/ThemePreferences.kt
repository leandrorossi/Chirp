package com.leandro.ur.core.domain.preferences

import kotlinx.coroutines.flow.Flow

interface ThemePreferences {
    fun observeThemePreference(): Flow<ThemePreference>
    suspend fun updateThemePreference(theme: ThemePreference)
}