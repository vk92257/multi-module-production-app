package com.bjs.pdanative.presentation.ui.lgvcheck

data class LgvCheck(
    var icon: Int,
    var title: String?,
    var description: String?,
    var faultDescription: String?,
    var selected: Boolean = false,
    var pass: Boolean = false,
    var fail: Boolean = false,
    var faultTitle: String?,
    var faultList: MutableList<Fault>? = ArrayList()
)
