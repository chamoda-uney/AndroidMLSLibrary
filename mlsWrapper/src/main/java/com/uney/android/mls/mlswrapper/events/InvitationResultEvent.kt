package com.uney.android.mls.mlswrapper.events


import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.GroupProfileResult
import com.uney.android.mls.mlswrapper.InvitationResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvitationResultEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, InvitationResult> {

    val subject: Subject<InvitationResult> =
        PublishSubject.create<InvitationResult>().toSerialized()

    init {
        bridgeMessageEvent.subject
            .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.INVITATION_RESULT }
            .map { it as InvitationResult }
            .subscribe(subject)

    }

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (InvitationResult) -> Unit) {
        subject.subscribe(observer)
    }
}