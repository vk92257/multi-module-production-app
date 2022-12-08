package com.bjs.pdanative.common.prefData

import android.content.Context
import android.content.SharedPreferences
import com.bjs.pdanative.common.AppController

//
// Created by Gaurav on 17/11/20.
// Copyright (c) 2020 BJS. All rights reserved.
//

//App shared preference
class AppPref private constructor() {

    companion object {
        val instance = AppPref()
    }


    val prefs: SharedPreferences by lazy {
        AppController.globalContext().getSharedPreferences(
            "${AppController.globalContext().packageName}.${AppPref::class.java.name}",
            Context.MODE_PRIVATE
        )
    }

    // To clear all preferences
    fun clear() {
        prefs.edit().clear().apply()
    }


    // Declare variables here whose value we wants to store in preferences
    // var token: String by AppDelegatePreference(prefs, TOKEN, "")


}