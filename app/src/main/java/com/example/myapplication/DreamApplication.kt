package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class DreamApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val 	key="1d0089160fd266e56c869d75b61addb6"
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
