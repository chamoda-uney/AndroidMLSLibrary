package com.uney.android.mls.mlswrapper

enum class ProducedBridgeMessageTypes {
    HTTP_RELAY_RESPONSE,
    XMPP_MESSAGE,
    PROCESS_PENDING_MESSAGES,
    ENCRYPT_MESSAGE,
    CREATE_GROUP,
    GENERATE_KEY_BUNDLE,
    ADD_MEMBERS,
    REMOVE_MEMBERS,
    GROUP_PROFILE
}

enum class ConsumedBridgeMessageTypes {
    HTTP_RELAY_REQUEST,
    GROUP_CREATE_RESULT,
    ADD_MEMBER_RESULT,
    REMOVE_MEMBER_RESULT,
    ENCRYPTED_MESSAGE,
    INVITATION_RESULT,
    DECRYPTED_MESSAGE,
    GROUP_PROFILE_RESULT
}

interface ConsumedBridgeMessage {
    val bridgeMessageType: ConsumedBridgeMessageTypes
}

interface ProducedBridgeMessage {
    val bridgeMessageType: ProducedBridgeMessageTypes
}

data class HttpRelayResponse(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.HTTP_RELAY_RESPONSE,
    val statusCode: Int,
    val responseHeaders: String,
    val responseBody: String
) : ProducedBridgeMessage

data class HttpRelayRequest(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.HTTP_RELAY_REQUEST,
    val endPoint: String,
    val method: String,
    val requestHeaders: String,
    val requestBody: String
) : ConsumedBridgeMessage

data class UploadKeyBundles(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.GENERATE_KEY_BUNDLE
) : ProducedBridgeMessage

data class ProcessPendingMessages(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.PROCESS_PENDING_MESSAGES
) : ProducedBridgeMessage

enum class GroupVisibility {
    PUBLIC,
    PRIVATE
}

enum class GroupType {
    SINGLE,
    MULTI,
    BROADCAST,
    UNRECOGNIZED
}

data class CreateGroup(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.CREATE_GROUP,
    val groupName: String,
    val visibility: GroupVisibility,
    val type: GroupType,
    val users: List<String>
) : ProducedBridgeMessage

data class GroupCreateResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.GROUP_CREATE_RESULT,
    val groupId: String
) : ConsumedBridgeMessage

data class InvitationResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.INVITATION_RESULT,
    val groupId: String,
    val senderUuid: String
) : ConsumedBridgeMessage

data class EncryptMessage(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.ENCRYPT_MESSAGE,
    val groupUuid: String,
    val payload: String
) : ProducedBridgeMessage

data class EncryptedMessage(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.ENCRYPTED_MESSAGE,
    val groupUuid: String,
    val payload: String
) : ConsumedBridgeMessage

data class DecryptedMessage(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.DECRYPTED_MESSAGE,
    val messageId: String,
    val clientMessageId: String,
    val groupUuid: String,
    val senderUuid: String,
    val senderClientUuid: String,
    val recipientClientUuid: String,
    val payload: String,
    val timestamp: Long,
    val messageType: String
) : ConsumedBridgeMessage

data class XMPPMessage(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.XMPP_MESSAGE,
    val messageId: String,
    val clientMessageId: String,
    val groupUuid: String,
    val senderUuid: String,
    val senderClientUuid: String,
    val recipientClientUuid: String,
    val payload: String,
    val timestamp: Long,
    val messageType: String
) : ProducedBridgeMessage

data class GroupProfile(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.GROUP_PROFILE,
    val groupUuid: String
) : ProducedBridgeMessage

data class GroupProfileResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.GROUP_PROFILE_RESULT,
    val groupId: String,
    val name: String,
    val type: String,
    val visibility: String,
    val groupVisibility: GroupVisibility,
    val groupType: GroupType,
    val members: List<Member>
) : ConsumedBridgeMessage {
    data class Member(
        val role: String,
        val status: String,
        val userId: String
    )
}