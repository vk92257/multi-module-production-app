package com.core.di

import android.content.Context
import androidx.room.Room
import com.core.data.local.PDANativeDatabase
import com.core.data.local.callLogs.CallLogsDao
import com.core.data.local.contacts.ContactDao
import com.core.data.local.repoImpl.CallLogRepoImpl
import com.core.data.local.repoImpl.ContactRepoImp
import com.core.domain.repository.CallLogRepository
import com.core.domain.repository.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun providesPDANativeDatabase(@ApplicationContext appContext: Context): PDANativeDatabase {
        return Room.databaseBuilder(
            appContext, PDANativeDatabase::class.java, "pda_native_database"
        ).build()
    }


    @Provides

    fun providesContactDao(database: PDANativeDatabase): ContactDao {
        return database.contactDao()
    }

    @Provides
    fun providesCallLogDao(database: PDANativeDatabase): CallLogsDao {
        return database.callLogDao()
    }


    /*Repository Providers for the contacts and call logs  */
    @Provides
    @Singleton
    fun providesContactRepository(dao: ContactDao): ContactRepository {
        return ContactRepoImp(dao)
    }

    @Provides
    @Singleton
    fun providesCallLogRepository(dao: CallLogsDao): CallLogRepository {
        return CallLogRepoImpl(dao)
    }


}