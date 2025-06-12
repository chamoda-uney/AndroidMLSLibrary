package com.uney.android.mls.mlswrapper.events

import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.GroupCreateResult
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CreateGroupResultEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, GroupCreateResult> {

    val subject: Observable<GroupCreateResult> = bridgeMessageEvent.subject
        .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.GROUP_CREATE_RESULT }
        .map { it as GroupCreateResult }
        .share()

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (GroupCreateResult) -> Unit) {
        subject.subscribe(observer)
    }
}