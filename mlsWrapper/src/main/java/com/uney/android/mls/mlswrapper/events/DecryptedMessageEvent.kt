package com.uney.android.mls.mlswrapper.events


import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.DecryptedMessage
import com.uney.android.mls.mlswrapper.GroupCreateResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DecryptedMessageEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, DecryptedMessage> {
    val subject: Subject<DecryptedMessage> =
        PublishSubject.create<DecryptedMessage>().toSerialized()

    init {
        bridgeMessageEvent.subject
            .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.DECRYPTED_MESSAGE }
            .map { it as DecryptedMessage }
            .subscribe(subject)
    }

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (DecryptedMessage) -> Unit) {
        subject.subscribe(observer)
    }
}