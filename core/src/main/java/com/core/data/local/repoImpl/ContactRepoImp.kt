package com.core.data.local.repoImpl

import com.core.data.local.contacts.ContactDao
import com.core.data.local.contacts.entity.ContactEntity
import com.core.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepoImp @Inject constructor(private val contactDao: ContactDao) : ContactRepository {
    override fun getAllContact() = contactDao.getAllContacts()

    override fun searchContact(phoneNumber: String) = contactDao.searchContact(phoneNumber)

    override fun insertContacts(contacts: List<ContactEntity>) =
        contactDao.insertAllContacts(contacts)

    override fun deleteAllContacts() = contactDao.deleteAllContacts()

}