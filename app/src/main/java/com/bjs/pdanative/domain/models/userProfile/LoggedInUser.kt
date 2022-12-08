package com.bjs.pdanative.domain.models.userProfile

data class LoggedInUser(
    var id: String = "D143",
    var name: String = "A.Singh",
    var image: String = "",
    var roleText: String = "Driver",
    var changeRoleText: String = "Change Role",
    var isTermsAndConditionAccepted : Boolean = false,
)
