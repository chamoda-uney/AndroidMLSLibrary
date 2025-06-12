package com.uney.android.mls.mlswrapper.events

interface BaseEvent<T, K> {
    /**
     * Sends the value.
     *
     * @param value The value to be sent
     */
    fun next(value: T) = Unit

    /**
     * Subscribes an observer.
     *
     * @param observer The observer to be subscribed
     */
    fun subscribe(observer: (K) -> Unit)
}