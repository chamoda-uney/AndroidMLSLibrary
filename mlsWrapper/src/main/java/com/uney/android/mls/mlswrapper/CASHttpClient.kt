package com.uney.android.mls.mlswrapper

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.Headers
import okhttp3.OkHttpClient
import okio.Buffer
import okio.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CASHttpClient @Inject constructor(
    private val logger: Logger,
    private val configuration: WrapperConfiguration
) {
    val instance: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(configuration.casTimeout, TimeUnit.MILLISECONDS)
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            //prepend base URL
            val originalUrl = originalRequest.url
            val newUrl = configuration.casBaseUrl + originalUrl.pathSegments.joinToString("/")

            //Attach access token
            val token = configuration.onAccessTokenRequested()
            val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .build()

            // Log request
            logger.debug(
                "CAS Request: ${
                    Json.encodeToString(JsonObject.serializer(), buildJsonObject {
                        put("headers", Json.encodeToJsonElement(newRequest.headers.toMultimap()))
                        put("url", Json.encodeToJsonElement(newRequest.url.toString()))
                        put("method", Json.encodeToJsonElement(newRequest.method))
                        newRequest.body?.let { body ->
                            if (body.contentLength() > 0) {
                                val buffer = Buffer()
                                body.writeTo(buffer)
                                put("body", Json.encodeToJsonElement(buffer.readUtf8()))
                            }
                        }
                    })
                }"
            )

            try {
                val response = chain.proceed(newRequest)

                // Filter headers to keep only string values
                val filteredHeaders = Headers.Builder().apply {
                    response.headers.forEach { (key, value) ->
                        if (value.isNotEmpty()) {
                            add(key, value)
                        }
                    }
                }.build()

                // Log response
                logger.debug(
                    "CAS Response: ${
                        Json.encodeToString(
                            JsonObject.serializer(),
                            buildJsonObject {
                                put("status", Json.encodeToJsonElement(response.code))
                                put(
                                    "headers",
                                    Json.encodeToJsonElement(filteredHeaders.toMultimap())
                                )
                                put("body", Json.encodeToJsonElement(response.body?.string()))
                            }
                        )
                    }"
                )

                // Create new response with filtered headers
                val newResponse = response.newBuilder()
                    .headers(filteredHeaders)
                    .build()

                // Handle non-500 errors as successful responses
                if (response.code >= 500) {
                    throw IOException("Server error: ${response.code}")
                } else if (!response.isSuccessful) {
                    return@addInterceptor newResponse
                }

                newResponse
            } catch (e: Exception) {
                throw e
            }
        }
        .build()


}