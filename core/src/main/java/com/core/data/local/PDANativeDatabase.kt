package com.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.data.local.callLogs.entity.CallLogsEntity
import com.core.data.local.callLogs.CallLogsDao
import com.core.data.local.callLogs.entity.ContactHistoryEntity
import com.core.data.local.contacts.ContactDao
import com.core.data.local.contacts.entity.ContactEntity

@Database(
    entities = [ContactEntity::class, CallLogsEntity::class, ContactHistoryEntity::class],
    version = 1
)
abstract class PDANativeDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun callLogDao (): CallLogsDao
}