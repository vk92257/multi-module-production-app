package com.core.domain.repository

import com.core.data.local.contacts.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getAllContact(): Flow<List<ContactEntity>>
    fun searchContact(phoneNumber: String): Flow<ContactEntity?>
    fun insertContacts(contacts: List<ContactEntity>)
    fun deleteAllContacts()
}