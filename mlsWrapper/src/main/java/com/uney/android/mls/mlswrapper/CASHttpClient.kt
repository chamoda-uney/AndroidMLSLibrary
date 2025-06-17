package com.uney.android.mls.mlswrapper

import com.google.gson.Gson
import com.google.gson.JsonObject
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
            val gson = Gson()

            //Attach access token
            val token = configuration.onAccessTokenRequested()
            val newRequest = originalRequest
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .build()

            // Log request
            logger.debug(
                "CAS Request: ${
                    gson.toJson(JsonObject().apply {
                        add("headers", gson.toJsonTree(newRequest.headers.toMultimap()))
                        addProperty("url", newRequest.url.toString())
                        addProperty("method", newRequest.method)
                        newRequest.body?.let { body ->
                            if (body.contentLength() > 0) {
                                val buffer = Buffer()
                                body.writeTo(buffer)
                                addProperty("body", buffer.readUtf8())
                            }
                        }
                    })
                }"
            )

            try {
                val response = chain.proceed(newRequest)



                // Log response
                val gson = Gson()
                logger.debug(
                    "CAS Response: ${
                        gson.toJson(JsonObject().apply {
                            addProperty("status", response.code)
                            add("headers", gson.toJsonTree(response.headers.toMultimap()))
                            response.let { response ->
                                response.body?.contentLength()?.let {
                                    if (it > 0) {
                                        val buffer = Buffer()
                                        buffer.write(response.body!!.bytes())
                                        addProperty("body", buffer.readUtf8())
                                    }
                                }
                            }
                        })
                    }"
                )

                // Create new response with filtered headers
                val newResponse = response.newBuilder()
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