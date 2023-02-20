package com.coming.app.fitmax.data

import android.util.Log

class CountSquat {
    var squat: Int = 0
    var isSquat: Boolean = true
    fun getValue(): Int {
        return squat
    }
    fun setCheckSquat(value: Int,checkSquat: Boolean){
        if (isSquat != checkSquat) {
            squat += value
            isSquat = checkSquat
        }
    }

    fun setValue( checkSquat: Boolean) {
            isSquat = checkSquat

    }

    fun plusSquat() {
        squat += 1
    }
}