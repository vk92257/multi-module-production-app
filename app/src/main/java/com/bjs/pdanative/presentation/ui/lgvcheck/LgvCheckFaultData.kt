package com.bjs.pdanative.presentation.ui.lgvcheck

/**
 * Created by deepak on {11/07/21}
 */
object LgvCheckFaultData {

    fun getFaultyComponents(): ArrayList<String> {
        val faultyComponentList: ArrayList<String> = ArrayList()
        faultyComponentList.add(0, "Faulty Component")
        faultyComponentList.add("Fuel Cap")
        return faultyComponentList
    }

    fun getFaults(): ArrayList<String> {
        val faultsList: ArrayList<String> = ArrayList()
        faultsList.add(0, "Fault")
        faultsList.add("Damaged")
        return faultsList
    }

    fun getPriority(): ArrayList<String> {
        val priorityList: ArrayList<String> = ArrayList()
        priorityList.add(0, "Priority")
        priorityList.add("Normal")
        priorityList.add("Medium")
        priorityList.add("Urgent")
        return priorityList
    }


}