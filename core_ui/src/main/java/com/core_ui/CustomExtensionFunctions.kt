package com.core_ui


fun String.removeLast(): String {
    return if (this.isEmpty()) this
    else this.take(this.length - 1)
}