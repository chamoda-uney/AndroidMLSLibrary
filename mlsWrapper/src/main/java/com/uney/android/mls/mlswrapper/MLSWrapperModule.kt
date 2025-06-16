package com.uney.android.mls.mlswrapper

import com.uney.android.mls.mlswrapper.events.Events
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MLSWrapperModule {

    private var config: WrapperConfiguration? = null

    fun setConfig(libraryConfig: WrapperConfiguration) {
        this.config = libraryConfig
    }

    @Provides
    @Singleton
    fun provideLibraryConfig(): WrapperConfiguration {
        return config ?: throw IllegalStateException("LibraryConfig must be set before use")
    }

    @Provides
    @Singleton
    fun provideMLSWrapper(
        config: WrapperConfiguration,
        methods: Methods,
        events: Events
    ): MLSWrapper {
        return MLSWrapper(config, methods, events)
    }

    @Provides
    fun provideMLSWrapperModule(): MLSWrapperModule {
        return this
    }

}