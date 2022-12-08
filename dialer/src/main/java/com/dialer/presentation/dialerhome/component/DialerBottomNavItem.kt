package com.dialer.presentation.dialerhome.component

import com.dialer.R

sealed class DialerBottomNavItem(
    var title: String, var icon: Int, var screen_route: String
) {
    object CallLog : DialerBottomNavItem("Call Log", R.drawable.phone, "call_log")
    object Dialer : DialerBottomNavItem("Dialer", R.drawable.contacts, "dialer")
    object ContactHistory : DialerBottomNavItem("ContactHistory",0,"contact_history")
}
