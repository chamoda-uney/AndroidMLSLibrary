package com.uney.android.mls.mlswrappertestapp


import java.util.Base64
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

// Represents a generic map for JWT payload
typealias JwtPayload = Map<String, Any?>

// Represents the JWT header
data class JwtHeader(
    val alg: String,
    val typ: String? = null,
    // Additional properties
    val additional: Map<String, Any?> = emptyMap()
)

// Represents a decoded JWT
data class DecodedJwt(
    val header: JwtHeader,
    val payload: JwtPayload,
    val signature: String
)

/**
 * Decodes a JWT token without verifying the signature.
 * @param token The JWT token to decode
 * @return Decoded JWT object containing header, payload, and signature
 * @throws IllegalArgumentException if the token is invalid
 */
fun decodeJwt(token: String): DecodedJwt {
    try {
        // Split the token into its three parts
        val parts = token.split(".")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT format: must contain header, payload, and signature")
        }
        val (headerB64, payloadB64, signature) = parts

        // Decode base64url strings
        fun decodeBase64Url(str: String): String {
            // Replace base64url characters with standard base64
            var decodedStr = str.replace("-", "+").replace("_", "/")
            // Add padding if needed
            val pad = decodedStr.length % 4
            if (pad > 0) {
                decodedStr += "=".repeat(4 - pad)
            }
            return String(Base64.getDecoder().decode(decodedStr))
        }

        // Parse header and payload
        val json = Json { ignoreUnknownKeys = true }
        val headerJson = json.parseToJsonElement(decodeBase64Url(headerB64)).jsonObject
        val payloadJson = json.parseToJsonElement(decodeBase64Url(payloadB64)).jsonObject

        val header = JwtHeader(
            alg = headerJson["alg"]?.toString()
                ?: throw IllegalArgumentException("Missing 'alg' in header"),
            typ = headerJson["typ"]?.toString(),
            additional = headerJson.filter { it.key !in listOf("alg", "typ") }
        )
        val payload = payloadJson.toMap()

        return DecodedJwt(header, payload, signature)
    } catch (e: Exception) {
        throw IllegalArgumentException("Failed to decode JWT: ${e.message ?: "Unknown error"}")
    }
}

// Extension function to convert JsonObject to Map<String, Any?>
private fun JsonObject.toMap(): Map<String, Any?> {
    return this.mapValues { it.value.toString() }
}