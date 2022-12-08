package com.core.domain.workManager

import com.core.domain.repository.CallLogRepository
import com.core.domain.repository.ContactRepository
import javax.inject.Inject

class UpdateAndDeleteDependency @Inject constructor(
    var callLogRepository: CallLogRepository, var contactRepository: ContactRepository
)