package com.uney.android.mls.mlswrapper

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Logger @Inject constructor() {

    val context = "ANDROID"

    fun debug(vararg args: String) {
        Log.d(context, "[DEBUG] ${args.joinToString(" ")}")
    }

    fun info(vararg args: String) {
        Log.i(context, "[INFO] ${args.joinToString(" ")}")
    }

    fun warn(vararg args: String) {
        Log.w(context, "[WARNING] ${args.joinToString(" ")}")
    }

    fun error(vararg args: String) {
        Log.e(context, "[ERROR] ${args.joinToString(" ")}")
    }
}