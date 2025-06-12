package com.uney.android.mls.mlswrapper.events

import com.uney.android.mls.mlswrapper.ConsumedBridgeMessage
import com.uney.android.mls.mlswrapper.InvitationResult
import javax.inject.Inject

class InvitationResultEvent @Inject constructor() :
    BaseEvent<ConsumedBridgeMessage, InvitationResult> {
    override fun subscribe(observer: (InvitationResult) -> Unit) {
        TODO("Not yet implemented")
    }
}