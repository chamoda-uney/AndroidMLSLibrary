package com.uney.android.mls.mlswrappertestapp

fun getDecodedJwtValue(key: String): String {
    val decodedJWT = decodeJwt(BuildConfig.MOCK_ACCESS_TOKEN)
    val value = decodedJWT.payload[key] as? String
    return value.toString()
}