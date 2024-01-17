package com.lesa.mycustomview

import android.graphics.Color
import android.util.Log

fun createGradient(
    color1: Argb10,
    color2: Argb10,
    size: Int
): List<Int> {
    Log.d("MyLog", "$color1  $color2")
    val stepR = - (color1.r - color2.r) / (size - 1 ).toFloat()
    val stepG = - (color1.g - color2.g) / (size - 1).toFloat()
    val stepB = - (color1.b - color2.b) / (size - 1).toFloat()
    val list = mutableListOf<Int>()
    val colorX = color1.copy()
    for (i in 1..size) {
        Log.d("MyLog", "$colorX")
        val color = Color.argb(colorX.a, colorX.r, colorX.g, colorX.b)
        list.add(color)
        colorX.r = (color1.r + stepR * i).toInt()
        colorX.g = (color1.g + stepG * i).toInt()
        colorX.b = (color1.b + stepB * i).toInt()
    }
    return list
}