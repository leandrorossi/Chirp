package com.leandrour.core.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.leandrour.core.data.dto.AuthInfoSerializable
import com.leandrour.core.data.mappers.toDomain
import com.leandrour.core.data.mappers.toSerializable
import com.leandrour.core.domain.auth.AuthInfo
import com.leandrour.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class DataStoreSessionStorageImpl(
    private val dataStore: DataStore<Preferences>
) : SessionStorage {

    private val authInfoKey = stringPreferencesKey("KEY_AUTH_INFO");

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun observeAuthInfo(): Flow<AuthInfo?> {
        return dataStore.data.map { prefs ->
            val serializedJson = prefs[authInfoKey]
            serializedJson?.let {
                json.decodeFromString<AuthInfoSerializable>(it).toDomain()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        if (info == null) {
            dataStore.edit {
                it.remove(authInfoKey)
            }
            return
        }

        val serialized = json.encodeToString(info.toSerializable())
        dataStore.edit { prefs ->
            prefs[authInfoKey] = serialized
        }
    }
}