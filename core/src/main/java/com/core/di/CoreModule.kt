package com.core.di

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object WorkMangerInitializer : Initializer<WorkManager> {
/*
    @Singleton
    @Provides
    fun providesUpdateAndDeleteWorkerDependency(
        contactRepo: ContactRepository, callLogRepository: CallLogRepository
    ): UpdateAndDeleteDependency {
        return UpdateAndDeleteDependency(
            callLogRepository, contactRepo
        )
    }*/

    override fun create(context: Context): WorkManager {
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}