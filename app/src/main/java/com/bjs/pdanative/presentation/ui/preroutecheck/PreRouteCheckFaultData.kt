package com.bjs.pdanative.presentation.ui.preroutecheck

/**
 * Created by deepak on {11/07/21}
 */
object PreRouteCheckFaultData {
    fun getFaultyComponents(): ArrayList<String> {
        val faultyComponentList: ArrayList<String> = ArrayList()
        faultyComponentList.add(0, "Fault")
        faultyComponentList.add("Not Required")
        faultyComponentList.add("Not Available")
        faultyComponentList.add("Faulty")
        faultyComponentList.add("Other")
        return faultyComponentList
    }

}