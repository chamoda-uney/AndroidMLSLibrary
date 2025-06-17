package com.uney.android.mls.mlswrapper.events


import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.BridgeMessageCodec
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.Logger
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("CheckResult")
@Singleton
class BridgeMessageEvent @Inject constructor(
    private val logger: Logger
) : BaseEvent<String, ConsumedBridgeMessage> {
    private val subjectIn: Subject<String> = PublishSubject.create<String>().toSerialized()
    val subject: Subject<ConsumedBridgeMessage> =
        PublishSubject.create<ConsumedBridgeMessage>().toSerialized()

    init {
        subjectIn
            .map {
                val decoded = BridgeMessageCodec.decodeConsumedMessage(it)
                decoded
            }
            .filter {
                ConsumedBridgeMessageTypes.entries.map { type -> type.name }
                    .contains(it.bridgeMessageType.toString())
            }
            .doOnNext { logger.debug("Bridge message received: $it") }.subscribe({
                subject.onNext(it)
            })
    }

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (ConsumedBridgeMessage) -> Unit) {
        subject.subscribe(observer)
    }

    override fun next(value: String) {
        subjectIn.onNext(value)
    }
}