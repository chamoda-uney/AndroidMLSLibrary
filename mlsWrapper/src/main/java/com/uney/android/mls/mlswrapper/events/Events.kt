package com.uney.android.mls.mlswrapper.events

import javax.inject.Inject

class Events @Inject constructor(
    val onMLSGroupCreate: CreateGroupResultEvent,
    val onMLSInvitation: InvitationResultEvent,
    val onEncryptedMessage: EncryptedMessageEvent,
    val onDecryptedMessage: DecryptedMessageEvent,
    val onGroupProfileResult: GroupProfileResultEvent
){
}