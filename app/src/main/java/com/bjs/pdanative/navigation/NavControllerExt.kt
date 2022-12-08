package com.bjs.pdanative.navigation

import androidx.navigation.NavController
import com.bjs.pdanative.util.UiEvent

/**
 * @Author: Vivek
 * @Date: 22/02/22
 */

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}