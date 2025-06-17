package com.uney.android.mls.mlswrapper.events


import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.EncryptedMessage
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedMessageEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
): BaseEvent<ConsumedBridgeMessage, EncryptedMessage> {
    val subject: Observable<EncryptedMessage> = bridgeMessageEvent.subject
        .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.ENCRYPTED_MESSAGE }
        .map { it as EncryptedMessage }
        .share()

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (EncryptedMessage) -> Unit) {
        subject.subscribe(observer)
    }
}