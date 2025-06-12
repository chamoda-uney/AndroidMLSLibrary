package com.uney.android.mls.mlswrapper

import android.util.Log

class Logger(val context: String) {

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