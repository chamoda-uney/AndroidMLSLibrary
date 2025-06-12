package com.uney.android.mls.mlswrapper

import com.uney.android.mls.mlswrapper.events.BridgeMessageEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import uniffi.uney_chat_sdk_uniffi.Relay
import uniffi.uney_chat_sdk_uniffi.e2eeSendEvent
import uniffi.uney_chat_sdk_uniffi.e2eeSetHttpRelayCallback
import javax.inject.Inject

class Bridge @Inject constructor(
    private val relay: BridgeRelayImplementation,
) {
    init {
        e2eeSetHttpRelayCallback(relay)
    }

    fun sendMessage(message: ProducedBridgeMessage) {
        val encoded = Json.encodeToString(message)
        val jsonObject = Json.parseToJsonElement(encoded).jsonObject
        val restMap =
            jsonObject.filterKeys { it != "bridgeMessageType" } //removing key bridgeMessageType
        val sanitized = Json.encodeToString(restMap)
        e2eeSendEvent(message.bridgeMessageType.toString(), sanitized)
    }
}

class BridgeRelayImplementation @Inject constructor(
    private val bridgeMessageEvent: BridgeMessageEvent
) : Relay {
    override fun delegateCall(operationParameterJsonString: String) {
        bridgeMessageEvent.next(operationParameterJsonString)
    }
}