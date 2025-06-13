package com.uney.android.mls.mlswrapper

interface WrapperConfiguration {
    val casBaseUrl: String
    val casTimeout: Long
    val uniqueClientId: String
    val cryptoStoragePath: String
    val onAccessTokenRequested: () -> String
}