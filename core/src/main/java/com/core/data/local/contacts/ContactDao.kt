package com.core.data.local.contacts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.core.data.local.contacts.entity.ContactEntity
import com.core.util.ContactsDBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query(" SELECT * FROM ContactEntity ")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query(" SELECT * FROM ContactEntity WHERE number LIKE :number OR contactWork LIKE :number OR contactHome LIKE :number ")
    fun searchContact(number: String): Flow<ContactEntity?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContacts(contacts: List<ContactEntity>)

    @Delete
    fun deleteContacts(user: ContactEntity)

    @Query(" DELETE  FROM ContactEntity")
    fun deleteAllContacts()


}