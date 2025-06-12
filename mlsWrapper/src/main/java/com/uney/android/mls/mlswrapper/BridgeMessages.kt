package com.uney.android.mls.mlswrapper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ProducedBridgeMessageTypes {
    @SerialName("HTTP_RELAY_RESPONSE") HTTP_RELAY_RESPONSE,
    @SerialName("XMPP_MESSAGE") XMPP_MESSAGE,
    @SerialName("PROCESS_PENDING_MESSAGES") PROCESS_PENDING_MESSAGES,
    @SerialName("ENCRYPT_MESSAGE") ENCRYPT_MESSAGE,
    @SerialName("CREATE_GROUP") CREATE_GROUP,
    @SerialName("GENERATE_KEY_BUNDLE") GENERATE_KEY_BUNDLE,
    @SerialName("ADD_MEMBERS") ADD_MEMBERS,
    @SerialName("REMOVE_MEMBERS") REMOVE_MEMBERS,
    @SerialName("GROUP_PROFILE") GROUP_PROFILE
}

@Serializable
enum class ConsumedBridgeMessageTypes {
    @SerialName("HTTP_RELAY_REQUEST") HTTP_RELAY_REQUEST,
    @SerialName("GROUP_CREATE_RESULT") GROUP_CREATE_RESULT,
    @SerialName("ADD_MEMBER_RESULT") ADD_MEMBER_RESULT,
    @SerialName("REMOVE_MEMBER_RESULT") REMOVE_MEMBER_RESULT,
    @SerialName("ENCRYPTED_MESSAGE") ENCRYPTED_MESSAGE,
    @SerialName("INVITATION_RESULT") INVITATION_RESULT,
    @SerialName("DECRYPTED_MESSAGE") DECRYPTED_MESSAGE,
    @SerialName("GROUP_PROFILE_RESULT") GROUP_PROFILE_RESULT
}

@Serializable
sealed class ConsumedBridgeMessage {
    abstract val bridgeMessageType: ConsumedBridgeMessageTypes
}

@Serializable
sealed class ProducedBridgeMessage {
    abstract val bridgeMessageType: ProducedBridgeMessageTypes
}

@Serializable
@SerialName("HTTP_RELAY_RESPONSE")
data class HttpRelayResponse(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.HTTP_RELAY_RESPONSE,
    val statusCode: Int,
    val responseHeaders: String,
    val responseBody: String
) : ProducedBridgeMessage()

@Serializable
@SerialName("HTTP_RELAY_REQUEST")
data class HttpRelayRequest(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.HTTP_RELAY_REQUEST,
    val endPoint: String,
    val method: String,
    val requestHeaders: String,
    val requestBody: String
) : ConsumedBridgeMessage()

@Serializable
@SerialName("GENERATE_KEY_BUNDLE")
data class UploadKeyBundles(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.GENERATE_KEY_BUNDLE
) : ProducedBridgeMessage()

@Serializable
@SerialName("PROCESS_PENDING_MESSAGES")
data class ProcessPendingMessages(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.PROCESS_PENDING_MESSAGES
) : ProducedBridgeMessage()

@Serializable
enum class GroupVisibility {
    @SerialName("PUBLIC") PUBLIC,
    @SerialName("PRIVATE") PRIVATE
}

@Serializable
enum class GroupType {
    @SerialName("SINGLE") SINGLE,
    @SerialName("MULTI") MULTI,
    @SerialName("BROADCAST") BROADCAST,
    @SerialName("UNRECOGNIZED") UNRECOGNIZED
}

@Serializable
@SerialName("CREATE_GROUP")
data class CreateGroup(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.CREATE_GROUP,
    val groupName: String,
    val visibility: GroupVisibility,
    val type: GroupType,
    val users: List<String>
) : ProducedBridgeMessage()

@Serializable
@SerialName("GROUP_CREATE_RESULT")
data class GroupCreateResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.GROUP_CREATE_RESULT,
    val groupId: String
) : ConsumedBridgeMessage()

@Serializable
@SerialName("INVITATION_RESULT")
data class InvitationResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.INVITATION_RESULT,
    val groupId: String,
    val senderUuid: String
) : ConsumedBridgeMessage()

@Serializable
@SerialName("ENCRYPT_MESSAGE")
data class EncryptMessage(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.ENCRYPT_MESSAGE,
    val groupUuid: String,
    val payload: String
) : ProducedBridgeMessage()

@Serializable
@SerialName("ENCRYPTED_MESSAGE")
data class EncryptedMessage(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.ENCRYPTED_MESSAGE,
    val groupUuid: String,
    val payload: String
) : ConsumedBridgeMessage()

@Serializable
@SerialName("DECRYPTED_MESSAGE")
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
) : ConsumedBridgeMessage()

@Serializable
@SerialName("XMPP_MESSAGE")
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
) : ProducedBridgeMessage()

@Serializable
@SerialName("GROUP_PROFILE")
data class GroupProfile(
    override val bridgeMessageType: ProducedBridgeMessageTypes = ProducedBridgeMessageTypes.GROUP_PROFILE,
    val groupUuid: String
) : ProducedBridgeMessage()

@Serializable
data class GroupMember(
    val role: String,
    val status: String,
    val userId: String
)

@Serializable
@SerialName("GROUP_PROFILE_RESULT")
data class GroupProfileResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.GROUP_PROFILE_RESULT,
    val groupId: String,
    val name: String,
    val type: String,
    val visibility: String,
    val groupVisibility: GroupVisibility,
    val groupType: GroupType,
    val members: List<GroupMember>
) : ConsumedBridgeMessage()


