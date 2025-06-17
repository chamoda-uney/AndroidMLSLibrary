package com.uney.android.mls.mlswrapper.events


import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.GroupCreateResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateGroupResultEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, GroupCreateResult> {

    val subject: Subject<GroupCreateResult> =
        PublishSubject.create<GroupCreateResult>().toSerialized()

    init {
        bridgeMessageEvent.subject
            .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.GROUP_CREATE_RESULT }
            .map { it as GroupCreateResult }
            .subscribe(subject)
    }


    @SuppressLint("CheckResult")
    override fun subscribe(observer: (GroupCreateResult) -> Unit) {
        subject.subscribe(observer)
    }
}