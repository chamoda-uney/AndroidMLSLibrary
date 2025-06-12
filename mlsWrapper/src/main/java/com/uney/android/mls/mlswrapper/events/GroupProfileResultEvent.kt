package com.uney.android.mls.mlswrapper.events

import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.GroupProfileResult
import javax.inject.Inject

class GroupProfileResultEvent @Inject constructor() :
    BaseEvent<ConsumedBridgeMessage, GroupProfileResult> {
    override fun subscribe(observer: (GroupProfileResult) -> Unit) {
        TODO("Not yet implemented")
    }
}