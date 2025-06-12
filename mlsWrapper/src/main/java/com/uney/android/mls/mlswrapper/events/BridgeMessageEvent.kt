package com.uney.android.mls.mlswrapper.events

import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.Logger
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject

class BridgeMessageEvent @Inject constructor(
    private val logger: Logger
) : BaseEvent<String, ConsumedBridgeMessage> {
    private val subjectIn: Subject<String> = PublishSubject.create<String>().toSerialized()
    val subject: Observable<ConsumedBridgeMessage> = subjectIn
        .map {
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                classDiscriminator = "bridgeMessageType"
            }
            json.decodeFromString(serializer<ConsumedBridgeMessage>(), it)
        }
        .filter {
            ConsumedBridgeMessageTypes.entries.map { type -> type.name }
                .contains(it.bridgeMessageType.toString())
        }
        .doOnNext { logger.debug("Bridge message received: $it") }
        .share()

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (ConsumedBridgeMessage) -> Unit) {
        subject.subscribe(observer)
    }

    override fun next(value: String) {
        subjectIn.onNext(value)
    }
}