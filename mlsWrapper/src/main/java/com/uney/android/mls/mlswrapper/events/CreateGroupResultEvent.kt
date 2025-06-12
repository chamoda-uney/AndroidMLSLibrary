package com.uney.android.mls.mlswrapper.events

import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.GroupCreateResult
import javax.inject.Inject

class CreateGroupResultEvent @Inject constructor() :
    BaseEvent<ConsumedBridgeMessage, GroupCreateResult> {
    override fun subscribe(observer: (GroupCreateResult) -> Unit) {
        TODO("Not yet implemented")
    }
}