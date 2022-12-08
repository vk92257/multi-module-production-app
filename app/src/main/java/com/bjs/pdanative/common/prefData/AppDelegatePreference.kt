package com.bjs.hgv.utils.prefData

import android.content.SharedPreferences
import kotlin.reflect.KProperty

//
// Created by gaurav on 17/11/20.
// Copyright (c) 2020 Bjs. All rights reserved.
//

class AppDelegatePreference<T>(val prefs : SharedPreferences,val key: String,val defaultValue : T) {



    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getPreference(key, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setPreference(key, value)
    }

    private fun getPreference(key: String, defaultValue: T): T {
        with(prefs) {
            val result: Any? = when (defaultValue) {
                is Boolean -> getBoolean(key, defaultValue)
                is Int -> getInt(key, defaultValue)
                is Long -> getLong(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is String -> getString(key, defaultValue)
                else -> throw IllegalArgumentException()
            }
            @Suppress("UNCHECKED_CAST")
            return result as T
        }
    }

    private fun setPreference(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException()
            }.apply()
        }
    }

}