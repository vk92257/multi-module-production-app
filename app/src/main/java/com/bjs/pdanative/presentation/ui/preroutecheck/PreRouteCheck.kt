package com.bjs.pdanative.presentation.ui.preroutecheck

data class PreRouteCheck(
    var icon: Int,
    var title: String?,
    var description: String?,
    var faultDescription: String?,
    var selected: Boolean = false,
    var yes: Boolean = false,
    var no: Boolean = false,
    var faultTitle: String?,
    var preCheckRouteFault: PreCheckRouteFault? = null
)
