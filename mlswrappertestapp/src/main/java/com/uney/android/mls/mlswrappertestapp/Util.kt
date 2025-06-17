package com.uney.android.mls.mlswrappertestapp

fun getDecodedJwtValue(key: String): String {
    val decodedJWT = decodeJwt(BuildConfig.MOCK_ACCESS_TOKEN)
    val value = decodedJWT.payload.properties[key]
    return when (value) {
        is String -> value
        else -> ""
    }
}