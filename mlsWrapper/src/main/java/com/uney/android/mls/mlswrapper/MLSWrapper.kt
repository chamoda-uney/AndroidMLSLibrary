package com.uney.android.mls.mlswrapper

import com.uney.android.mls.mlswrapper.events.Events
import uniffi.uney_chat_sdk_uniffi.e2eeInit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MLSWrapper @Inject constructor(
    private val config: WrapperConfiguration,
    val methods: Methods,
    val events: Events
) {
    fun initlaize() {
        e2eeInit(
            clientUniqueId = config.uniqueClientId,
            storagePath = config.cryptoStoragePath,
            encryptionKey = "",
            casBaseUrl = config.casBaseUrl,
            tracingLevel = "DEBUG"
        )
    }

}