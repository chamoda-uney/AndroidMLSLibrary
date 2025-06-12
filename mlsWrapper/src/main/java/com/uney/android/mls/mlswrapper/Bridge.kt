package com.uney.android.mls.mlswrapper

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import uniffi.uney_chat_sdk_uniffi.Relay
import uniffi.uney_chat_sdk_uniffi.e2eeSendEvent
import uniffi.uney_chat_sdk_uniffi.e2eeSetHttpRelayCallback

class Bridge {
    fun sendMessage(message: ProducedBridgeMessage) {
        val encoded = Json.encodeToString(message)
        val jsonObject = Json.parseToJsonElement(encoded).jsonObject
        val restMap =
            jsonObject.filterKeys { it != "bridgeMessageType" } //removing key bridgeMessageType
        val sanitized = Json.encodeToString(restMap)

        e2eeSendEvent(message.bridgeMessageType.toString(), sanitized)
    }


    fun initializeBridge() {
        e2eeSetHttpRelayCallback()
    }
}

class BridgeRelayImplementation : Relay {
    override fun delegateCall(operationParameterJsonString: String) {
        TODO("Not yet implemented")
    }

}