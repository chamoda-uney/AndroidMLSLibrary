package com.uney.android.mls.mlswrapper.events

import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import javax.inject.Inject

class BridgeMessageEvent @Inject constructor() : BaseEvent<String, ConsumedBridgeMessage> {
    override fun subscribe(observer: (ConsumedBridgeMessage) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun next(value: String) {
        super.next(value)
    }
}