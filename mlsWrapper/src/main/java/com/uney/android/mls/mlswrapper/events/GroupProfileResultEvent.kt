package com.uney.android.mls.mlswrapper.events

import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.ConsumedBridgeMessageTypes
import com.uney.android.mls.mlswrapper.GroupProfileResult
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupProfileResultEvent @Inject constructor(
    bridgeMessageEvent: BridgeMessageEvent
) :
    BaseEvent<ConsumedBridgeMessage, GroupProfileResult> {
    val subject: Observable<GroupProfileResult> = bridgeMessageEvent.subject
        .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.GROUP_PROFILE_RESULT }
        .map { it as GroupProfileResult }
        .share()

    @SuppressLint("CheckResult")
    override fun subscribe(observer: (GroupProfileResult) -> Unit) {
        subject.subscribe(observer)
    }
}