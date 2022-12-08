package com.dialer.presentation.contact

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.ContactRepository
import com.dialer.domain.model.ContactPojo
import com.dialer.domain.model.toContactPojo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    contactsRepository: ContactRepository
) : ViewModel() {
    var state: SnapshotStateList<ContactPojo> = mutableStateListOf()
        private set
    private var stateCopy = mutableStateListOf<ContactPojo>()

    init {
        viewModelScope.launch {
            val list = mutableStateListOf<ContactPojo>()

            contactsRepository.getAllContact().collectLatest {
                if (state.isNotEmpty()) state.clear()
                if (stateCopy.isNotEmpty()) stateCopy.clear()
                it.map { contactEntity ->
                    list.add(contactEntity.toContactPojo())

                }
                state.addAll(list)
                stateCopy.addAll(list)

            }
        }

    }


    fun onContactEvent(event: ContactScreenEvent) {
        when (event) {
            is ContactScreenEvent.OnSearchEvent -> {
                if (event.text.isNotEmpty()) {
                    val searchText = event.text.lowercase().trim()
                    stateCopy.filter {
                        (it.name.lowercase().trim()
                            .contains(searchText.lowercase()) || it.postcode.lowercase().trim()
                            .contains(searchText.lowercase()) || it.carrierReference.lowercase()
                            .trim().contains(searchText.lowercase()))
                    }.also {
                        if (state.isNotEmpty()) state.clear()
                        state.addAll(it.toMutableStateList())
                    }
                } else {
                    state.clear()
                    state.addAll(stateCopy)
                }
            }
        }
    }

}