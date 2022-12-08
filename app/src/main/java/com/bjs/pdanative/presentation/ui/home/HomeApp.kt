package com.bjs.pdanative.presentation.ui.home

data class HomeApp(
    var appTypeName: String = "",
    var appListData: MutableList<AppInfo>? = ArrayList()
)
