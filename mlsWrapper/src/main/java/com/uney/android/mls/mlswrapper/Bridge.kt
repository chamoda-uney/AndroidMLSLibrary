package com.uney.android.mls.mlswrapper

import com.uney.android.mls.mlswrapper.events.BridgeMessageEvent
import uniffi.uney_chat_sdk_uniffi.Relay
import uniffi.uney_chat_sdk_uniffi.e2eeSendEvent
import uniffi.uney_chat_sdk_uniffi.e2eeSetHttpRelayCallback
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Bridge @Inject constructor(
    private val relay: BridgeRelayImplementation,
    private val logger: Logger
) {
    init {
        e2eeSetHttpRelayCallback(relay)
    }

    fun sendMessage(message: ProducedBridgeMessage) {
        val encoded = BridgeMessageCodec.encodeAndSanitizeProducedMessage(message)
        logger.debug("Sending bridge message ${message.bridgeMessageType}", encoded)
        e2eeSendEvent(message.bridgeMessageType.toString(), encoded)
    }
}

@Singleton
class BridgeRelayImplementation @Inject constructor(
    private val bridgeMessageEvent: BridgeMessageEvent
) : Relay {
    override fun delegateCall(operationParameterJsonString: String) {
        bridgeMessageEvent.next(operationParameterJsonString)
    }
}