package com.leandrour.core.domain.util

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

class Paginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<List<Item>, DataError>,
    private val getNextKey: suspend (items: List<Item>) -> Key,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) {
    private var currentKey = initialKey
    private var isMakingRequest = false
    private var lastRequestKey: Key? = null

    suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }

        if (currentKey != null && currentKey == lastRequestKey) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)

        try {
            onRequest(currentKey)
                .onSuccess { items ->
                    val newKey = getNextKey(items)
                    onSuccess(items, newKey)
                    lastRequestKey = currentKey

                    currentKey = newKey
                }
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            onError(e)
        } finally {
            onLoadUpdated(false)
            isMakingRequest = false
        }
    }

    fun reset() {
        currentKey = initialKey
        lastRequestKey = null
    }
}