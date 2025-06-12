package com.uney.android.mls.mlswrapper.events

import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.EncryptedMessage
import javax.inject.Inject

class EncryptedMessageEvent @Inject constructor(): BaseEvent<ConsumedBridgeMessage, EncryptedMessage> {
    override fun subscribe(observer: (EncryptedMessage) -> Unit) {
        TODO("Not yet implemented")
    }
}