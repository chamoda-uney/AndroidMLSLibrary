package com.uney.android.mls.mlswrapper.events

import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.DecryptedMessage
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DecryptedMessageEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, DecryptedMessage> {
    val subject: Observable<DecryptedMessage> = bridgeMessageEvent.subject
        .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.DECRYPTED_MESSAGE }
        .map { it as DecryptedMessage }
        .share()

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (DecryptedMessage) -> Unit) {
        subject.subscribe(observer)
    }
}