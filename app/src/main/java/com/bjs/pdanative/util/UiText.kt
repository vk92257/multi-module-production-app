package com.bjs.pdanative.util

import android.content.Context

/**
 * @Author: Vivek
 * @Date: 22/02/22
 */
sealed class UiText {
    data class DynamicString(val text :String ): UiText()
    data class ResourceString(val resid: Int ): UiText()

    fun asString(context: Context): String{
        return  when(this){
            is ResourceString -> context.getString(resid)
            is DynamicString -> text
        }
    }
}