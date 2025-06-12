package com.uney.android.mls.mlswrapper.events

import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.InvitationResult
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class InvitationResultEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, InvitationResult> {
    val subject: Observable<InvitationResult> = bridgeMessageEvent.subject
        .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.INVITATION_RESULT }
        .map { it as InvitationResult }
        .share()

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (InvitationResult) -> Unit) {
        subject.subscribe(observer)
    }
}