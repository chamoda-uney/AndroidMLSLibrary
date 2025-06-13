package com.uney.android.mls.mlswrapper

import android.annotation.SuppressLint
import com.uney.android.mls.mlswrapper.events.BridgeMessageEvent
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import javax.inject.Inject


@SuppressLint("CheckResult")
class HTTPRequestDelegator @Inject constructor(
    private val bridge: Bridge,
    private val bridgeMessageEvents: BridgeMessageEvent,
    private val httpService: CASHttpClient
) {
    private val subject: Subject<ConsumedBridgeMessage> =
        PublishSubject.create<ConsumedBridgeMessage>().toSerialized()

    val callback = object : Callback {
        override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
            throw RuntimeException("HTTP request failed", e)
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            val responseBridgeMessage = HttpRelayResponse(
                bridgeMessageType = ProducedBridgeMessageTypes.HTTP_RELAY_RESPONSE,
                statusCode = response.code,
                responseHeaders = Json.encodeToString(
                    MapSerializer(String.serializer(), String.serializer()),
                    response.headers.toMap()
                ),
                responseBody = response.body.toString()
            )
            bridge.sendMessage(responseBridgeMessage)
        }

    }


    init {
        bridgeMessageEvents.subject
            .subscribe { subject.onNext(it) }
        subject
            .filter { it.bridgeMessageType == ConsumedBridgeMessageTypes.HTTP_RELAY_REQUEST }
            .map { it as HttpRelayRequest }
            .subscribe { message ->
                val headers: Map<String, String> = Json.decodeFromString(
                    MapSerializer(String.serializer(), String.serializer()),
                    message.requestHeaders
                )
                when (message.method) {
                    "GET" -> httpService.instance.newCall(
                        Request.Builder()
                            .get()
                            .url(message.endPoint)
                            .apply {
                                headers.forEach { (key, value) -> header(key, value) }
                            }
                            .build()
                    ).enqueue(callback)

                    "POST" -> httpService.instance.newCall(
                        Request.Builder()
                            .post(getApplicationJsonRequestBody(message.requestBody))
                            .url(message.endPoint)
                            .apply {
                                headers.forEach { (key, value) -> header(key, value) }
                            }
                            .build()
                    ).enqueue(callback)

                    "PUT" -> httpService.instance.newCall(
                        Request.Builder()
                            .put(getApplicationJsonRequestBody(message.requestBody))
                            .url(message.endPoint)
                            .apply {
                                headers.forEach { (key, value) -> header(key, value) }
                            }
                            .build()
                    ).enqueue(callback)

                    "DELETE" -> httpService.instance.newCall(
                        Request.Builder()
                            .delete(getApplicationJsonRequestBody(message.requestBody))
                            .url(message.endPoint)
                            .apply {
                                headers.forEach { (key, value) -> header(key, value) }
                            }
                            .build()
                    ).enqueue(callback)

                    else -> throw IllegalArgumentException("Unsupported HTTP method: ${message.method}")
                }

            }
    }

    private fun getApplicationJsonRequestBody(body: String): RequestBody {
        return body.toRequestBody("application/json".toMediaType())
    }
}