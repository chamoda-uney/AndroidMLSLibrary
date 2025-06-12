package com.uney.android.mls.mlswrapper.events

import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.DecryptedMessage
import javax.inject.Inject

class DecryptedMessageEvent @Inject constructor() :
    BaseEvent<ConsumedBridgeMessage, DecryptedMessage> {
    override fun subscribe(observer: (DecryptedMessage) -> Unit) {
        TODO("Not yet implemented")
    }
}