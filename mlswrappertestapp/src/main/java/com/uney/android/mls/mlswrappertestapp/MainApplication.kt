package com.uney.android.mls.mlswrappertestapp

import android.app.Application
import android.util.Log
import com.uney.android.mls.mlswrapper.MLSWrapperModule
import com.uney.android.mls.mlswrapper.WrapperConfiguration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var mlsWrapperModule: MLSWrapperModule

    override fun onCreate() {
        super.onCreate()
        //sample get the storage path
        val internalStoragePath: String = this.filesDir.absolutePath

        val config = WrapperConfigurationImpl()
        config.cryptoStoragePath = internalStoragePath;
        mlsWrapperModule.setConfig(config)

    }
}

class WrapperConfigurationImpl : WrapperConfiguration {
    override val casBaseUrl: String = BuildConfig.CAS_BASE_URL
    override val casTimeout: Long = 10_000 // 30 seconds in milliseconds
    override val uniqueClientId: String = this.getDecodedJwtValue("uniqueDeviceId")
    override var cryptoStoragePath: String = ""
    override val onAccessTokenRequested: () -> String = {
        BuildConfig.MOCK_ACCESS_TOKEN
    }


    private fun getDecodedJwtValue(key: String): String {
        val decodedJWT = decodeJwt(BuildConfig.MOCK_ACCESS_TOKEN)
        val uniqueClientId = decodedJWT.payload[key] as? String
        return uniqueClientId.toString()
    }
}