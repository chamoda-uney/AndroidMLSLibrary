package com.uney.android.mls.mlswrappertestapp

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.util.Base64

data class JwtHeader(
    val alg: String,
    val typ: String? = null,
    val additionalProperties: Map<String, Any> = emptyMap()
)

data class JwtPayload(
    val properties: Map<String, Any> = emptyMap()
)

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
            val base64 = str.replace("-", "+").replace("_", "/")
            // Add padding if needed
            val pad = base64.length % 4
            val padded = if (pad > 0) base64 + "=".repeat(4 - pad) else base64
            return String(Base64.getDecoder().decode(padded))
        }

        // Parse header and payload
        val gson = Gson()
        val headerJson = decodeBase64Url(headerB64)
        val payloadJson = decodeBase64Url(payloadB64)

        // Parse header into JsonObject and extract fields
        val headerObj = gson.fromJson(headerJson, JsonObject::class.java)
        val alg = headerObj.get("alg")?.asString
            ?: throw IllegalArgumentException("Missing 'alg' in JWT header")
        val typ = headerObj.get("typ")?.asString
        // Collect additional properties
        val headerProps = mutableMapOf<String, Any>()
        headerObj.entrySet().forEach { (key, value) ->
            if (key != "alg" && key != "typ") {
                headerProps[key] = value
            }
        }

        // Parse payload into Map
        val payloadType = object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type
        val payloadProps = gson.fromJson<Map<String, Any>>(payloadJson, payloadType)

        return DecodedJwt(
            header = JwtHeader(alg, typ, headerProps),
            payload = JwtPayload(payloadProps),
            signature = signature
        )
    } catch (e: Exception) {
        throw IllegalArgumentException("Failed to decode JWT: ${e.message ?: "Unknown error"}")
    }
}