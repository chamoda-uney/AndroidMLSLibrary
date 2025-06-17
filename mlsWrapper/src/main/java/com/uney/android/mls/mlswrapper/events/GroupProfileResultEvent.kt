package com.uney.android.mls.mlswrapper.events


import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.EncryptedMessage
import com.uney.android.mls.mlswrapper.GroupProfileResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupProfileResultEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, GroupProfileResult> {

    val subject: Subject<GroupProfileResult> =
        PublishSubject.create<GroupProfileResult>().toSerialized()

    init {
        bridgeMessageEvent.subject
            .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.GROUP_PROFILE_RESULT }
            .map { it as GroupProfileResult }
            .subscribe(subject)
    }

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (GroupProfileResult) -> Unit) {
        subject.subscribe(observer)
    }
}