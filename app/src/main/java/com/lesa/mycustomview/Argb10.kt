package com.lesa.mycustomview

import android.graphics.Color

data class Argb10(
    val a: Int = 255,
    var r: Int,
    var g: Int,
    var b: Int,
) {
    companion object {
        fun createRandomArgb10(): Argb10 {
            val r = (0..255).random()
            val g = (0..255).random()
            val b = (0..255).random()
            return Argb10(r = r, g = g, b = b)
        }
    }
}
