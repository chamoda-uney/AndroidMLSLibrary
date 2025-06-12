package com.uney.android.mls.mlswrapper

class Methods {
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
}