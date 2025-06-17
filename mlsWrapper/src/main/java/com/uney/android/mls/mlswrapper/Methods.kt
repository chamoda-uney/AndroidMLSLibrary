package com.uney.android.mls.mlswrapper


import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class Methods @Inject constructor(
    private val bridge: Bridge,
    private val casHttpClient: CASHttpClient,
    private val configuration: WrapperConfiguration

) {
    /**
     * Generates a new key bundle and uploads it to the server.
     */
    fun uploadKeyBundles() {
        bridge.sendMessage(UploadKeyBundles())
    }

    /**
     * Processes all pending messages.
     *
     * The method sends a [ProducedBridgeMessageTypes.PROCESS_PENDING_MESSAGES] message to the bridge.
     * The bridge will then process all pending messages.
     */
    fun processPendingMessages() {
        bridge.sendMessage(ProcessPendingMessages())
    }

    /**
     * Creates a new group with the specified parameters.
     *
     * This method sends a [ProducedBridgeMessageTypes.CREATE_GROUP] message to the bridge
     * with the provided group parameters, initiating the creation of a new group.
     *
     * @param groupName The name of the group to create.
     * @param visibility The visibility of the group (PUBLIC or PRIVATE).
     * @param type The type of the group (SINGLE, MULTI, BROADCAST, or UNRECOGNIZED).
     * @param users The list of user IDs to include in the group.
     */
    fun createGroup(
        groupName: String,
        visibility: GroupVisibility,
        type: GroupType,
        users: List<String>
    ) {
        bridge.sendMessage(
            CreateGroup(
                groupName = groupName,
                visibility = visibility,
                type = type,
                users = users
            )
        )
    }

    /**
     * Encrypts a message with the specified parameters.
     *
     * This method sends a [ProducedBridgeMessageTypes.ENCRYPT_MESSAGE] message to the bridge
     * with the provided encryption parameters.
     *
     * @param groupUuid The UUID of the group for the message.
     * @param payload The message payload to encrypt.
     */
    fun encryptMessage(groupUuid: String, payload: String) {
        bridge.sendMessage(
            EncryptMessage(
                groupUuid = groupUuid,
                payload = payload
            )
        )
    }

    /**
     * Sends an XMPP message to the bridge.
     *
     * This method sends a [ProducedBridgeMessageTypes.XMPP_MESSAGE] message to the bridge
     * with the provided XMPP message parameters.
     *
     * @param messageId The ID of the message.
     * @param clientMessageId The client-specific message ID.
     * @param groupUuid The UUID of the group.
     * @param senderUuid The UUID of the sender.
     * @param senderClientUuid The UUID of the sender's client.
     * @param recipientClientUuid The UUID of the recipient's client.
     * @param payload The message payload.
     * @param timestamp The timestamp of the message.
     * @param messageType The type of the message.
     */
    fun sendXmppMessage(
        messageId: String,
        clientMessageId: String,
        groupUuid: String,
        senderUuid: String,
        senderClientUuid: String,
        recipientClientUuid: String,
        payload: String,
        timestamp: Long,
        messageType: String
    ) {
        bridge.sendMessage(
            XMPPMessage(
                messageId = messageId,
                clientMessageId = clientMessageId,
                groupUuid = groupUuid,
                senderUuid = senderUuid,
                senderClientUuid = senderClientUuid,
                recipientClientUuid = recipientClientUuid,
                payload = payload,
                timestamp = timestamp,
                messageType = messageType
            )
        )
    }

    /**
     * Retrieves the profile of a group from the bridge.
     *
     * This method sends a [ProducedBridgeMessageTypes.GROUP_PROFILE] message to the bridge
     * with the provided group UUID.
     *
     * @param groupUuid The UUID of the group whose profile is to be retrieved.
     */
    fun groupProfile(groupUuid: String) {
        bridge.sendMessage(
            GroupProfile(
                groupUuid = groupUuid
            )
        )
    }

    /**
     * Temporarily registers a user on the CAS server.
     *
     * This method sends a POST request to the CAS server to create a new user with the specified client ID and user ID.
     * @deprecated This method is deprecated and will be removed in a future version.
     * @param clientId The client ID for the user to be registered.
     * @param userId The user ID for the user to be registered.
     */

    suspend fun tempRegisterUserOnCAS(clientId: String, userId: String) {
        val encoded = Gson().toJson(JsonObject().apply {
            addProperty("clientId", clientId)
            addProperty("userId", userId)
        })

        val requestBody = encoded.toRequestBody("application/json".toMediaType())
        suspendCoroutine { continuation ->
            casHttpClient.instance.newCall(
                Request.Builder()
                    .post(requestBody)
                    .url(configuration.casBaseUrl + "/public/api/v1/users")
                    .addHeader("x-kchat-correlation-id", "d6ba2f37-a9a1-4e5a-8132-9cb452a20856")
                    .build()
            ).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(Exception("Request failed with code ${response.code}"))
                    }
                }
            })
        }
    }
}