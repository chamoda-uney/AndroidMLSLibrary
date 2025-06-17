package com.uney.android.mls.mlswrapper


import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

// Enums
enum class ProducedBridgeMessageTypes {
    @SerializedName("HTTP_RELAY_RESPONSE")
    HTTP_RELAY_RESPONSE,

    @SerializedName("XMPP_MESSAGE")
    XMPP_MESSAGE,

    @SerializedName("PROCESS_PENDING_MESSAGES")
    PROCESS_PENDING_MESSAGES,

    @SerializedName("ENCRYPT_MESSAGE")
    ENCRYPT_MESSAGE,

    @SerializedName("CREATE_GROUP")
    CREATE_GROUP,

    @SerializedName("GENERATE_KEY_BUNDLE")
    GENERATE_KEY_BUNDLE,

    @SerializedName("ADD_MEMBERS")
    ADD_MEMBERS,

    @SerializedName("REMOVE_MEMBERS")
    REMOVE_MEMBERS,

    @SerializedName("GROUP_PROFILE")
    GROUP_PROFILE
}

enum class ConsumedBridgeMessageTypes {
    @SerializedName("HTTP_RELAY_REQUEST")
    HTTP_RELAY_REQUEST,

    @SerializedName("GROUP_CREATE_RESULT")
    GROUP_CREATE_RESULT,

    @SerializedName("ADD_MEMBER_RESULT")
    ADD_MEMBER_RESULT,

    @SerializedName("REMOVE_MEMBER_RESULT")
    REMOVE_MEMBER_RESULT,

    @SerializedName("ENCRYPTED_MESSAGE")
    ENCRYPTED_MESSAGE,

    @SerializedName("INVITATION_RESULT")
    INVITATION_RESULT,

    @SerializedName("DECRYPTED_MESSAGE")
    DECRYPTED_MESSAGE,

    @SerializedName("GROUP_PROFILE_RESULT")
    GROUP_PROFILE_RESULT
}

enum class GroupVisibility {
    @SerializedName("PUBLIC")
    PUBLIC,

    @SerializedName("PRIVATE")
    PRIVATE
}

enum class GroupType {
    @SerializedName("SINGLE")
    SINGLE,

    @SerializedName("MULTI")
    MULTI,

    @SerializedName("BROADCAST")
    BROADCAST,

    @SerializedName("UNRECOGNIZED")
    UNRECOGNIZED
}

// Base interfaces
interface ConsumedBridgeMessage {
    val bridgeMessageType: ConsumedBridgeMessageTypes
}

interface ProducedBridgeMessage {
    val bridgeMessageType: ProducedBridgeMessageTypes
}

// Data classes implementing the interfaces
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

data class GroupMember(
    val role: String,
    val status: String,
    val userId: String
)

data class GroupProfileResult(
    override val bridgeMessageType: ConsumedBridgeMessageTypes = ConsumedBridgeMessageTypes.GROUP_PROFILE_RESULT,
    val groupId: String,
    val name: String,
    val type: String,
    val visibility: String,
    val groupVisibility: GroupVisibility,
    val groupType: GroupType,
    val members: List<GroupMember>
) : ConsumedBridgeMessage

// Custom deserializers for polymorphic deserialization
class ConsumedBridgeMessageDeserializer : JsonDeserializer<ConsumedBridgeMessage> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ConsumedBridgeMessage {
        val jsonObject = json.asJsonObject
        val messageType = jsonObject.get("bridgeMessageType").asString

        return when (ConsumedBridgeMessageTypes.valueOf(messageType)) {
            ConsumedBridgeMessageTypes.HTTP_RELAY_REQUEST ->
                context.deserialize(json, HttpRelayRequest::class.java)

            ConsumedBridgeMessageTypes.GROUP_CREATE_RESULT ->
                context.deserialize(json, GroupCreateResult::class.java)

            ConsumedBridgeMessageTypes.ADD_MEMBER_RESULT ->
                throw NotImplementedError("ADD_MEMBER_RESULT not implemented")

            ConsumedBridgeMessageTypes.REMOVE_MEMBER_RESULT ->
                throw NotImplementedError("REMOVE_MEMBER_RESULT not implemented")

            ConsumedBridgeMessageTypes.ENCRYPTED_MESSAGE ->
                context.deserialize(json, EncryptedMessage::class.java)

            ConsumedBridgeMessageTypes.INVITATION_RESULT ->
                context.deserialize(json, InvitationResult::class.java)

            ConsumedBridgeMessageTypes.DECRYPTED_MESSAGE ->
                context.deserialize(json, DecryptedMessage::class.java)

            ConsumedBridgeMessageTypes.GROUP_PROFILE_RESULT ->
                context.deserialize(json, GroupProfileResult::class.java)
        }
    }
}

class ProducedBridgeMessageDeserializer : JsonDeserializer<ProducedBridgeMessage> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ProducedBridgeMessage {
        val jsonObject = json.asJsonObject
        val messageType = jsonObject.get("bridgeMessageType").asString

        return when (ProducedBridgeMessageTypes.valueOf(messageType)) {
            ProducedBridgeMessageTypes.HTTP_RELAY_RESPONSE ->
                context.deserialize(json, HttpRelayResponse::class.java)

            ProducedBridgeMessageTypes.XMPP_MESSAGE ->
                context.deserialize(json, XMPPMessage::class.java)

            ProducedBridgeMessageTypes.PROCESS_PENDING_MESSAGES ->
                context.deserialize(json, ProcessPendingMessages::class.java)

            ProducedBridgeMessageTypes.ENCRYPT_MESSAGE ->
                context.deserialize(json, EncryptMessage::class.java)

            ProducedBridgeMessageTypes.CREATE_GROUP ->
                context.deserialize(json, CreateGroup::class.java)

            ProducedBridgeMessageTypes.GENERATE_KEY_BUNDLE ->
                context.deserialize(json, UploadKeyBundles::class.java)

            ProducedBridgeMessageTypes.ADD_MEMBERS ->
                throw NotImplementedError("ADD_MEMBERS not implemented")

            ProducedBridgeMessageTypes.REMOVE_MEMBERS ->
                throw NotImplementedError("REMOVE_MEMBERS not implemented")

            ProducedBridgeMessageTypes.GROUP_PROFILE ->
                context.deserialize(json, GroupProfile::class.java)
        }
    }
}

// Utility class for encoding/decoding messages
object BridgeMessageCodec {
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(ConsumedBridgeMessage::class.java, ConsumedBridgeMessageDeserializer())
        .registerTypeAdapter(ProducedBridgeMessage::class.java, ProducedBridgeMessageDeserializer())
        .create()

    // Encode functions
    fun encodeConsumedMessage(message: ConsumedBridgeMessage): String {
        return gson.toJson(message)
    }

    fun encodeProducedMessage(message: ProducedBridgeMessage): String {
        return gson.toJson(message)
    }

    // Decode functions
    fun decodeConsumedMessage(json: String): ConsumedBridgeMessage {
        return gson.fromJson(json, ConsumedBridgeMessage::class.java)
    }

    fun decodeProducedMessage(json: String): ProducedBridgeMessage {
        return gson.fromJson(json, ProducedBridgeMessage::class.java)
    }

    // Encode and sanitize (remove bridgeMessageType key)
    fun encodeAndSanitizeProducedMessage(message: ProducedBridgeMessage): String {
        val encoded = gson.toJson(message)
        val jsonObject = gson.fromJson(encoded, JsonObject::class.java)
        jsonObject.remove("bridgeMessageType")
        return gson.toJson(jsonObject)
    }

    fun encodeAndSanitizeConsumedMessage(message: ConsumedBridgeMessage): String {
        val encoded = gson.toJson(message)
        val jsonObject = gson.fromJson(encoded, JsonObject::class.java)
        jsonObject.remove("bridgeMessageType")
        return gson.toJson(jsonObject)
    }

    // Type-specific decode functions for convenience
    internal inline fun <reified T : ConsumedBridgeMessage> decodeConsumedMessageAs(json: String): T {
        return gson.fromJson(json, T::class.java)
    }

    internal inline fun <reified T : ProducedBridgeMessage> decodeProducedMessageAs(json: String): T {
        return gson.fromJson(json, T::class.java)
    }
}