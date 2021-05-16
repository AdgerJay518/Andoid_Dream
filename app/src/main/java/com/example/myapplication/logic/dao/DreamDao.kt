package com.example.myapplication.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.myapplication.DreamApplication
import com.example.myapplication.logic.model.Dream
import com.google.gson.Gson

object DreamDao {
    fun saveDream(dream: Dream) {
        sharedPreferences().edit {
            putString("dream", Gson().toJson(dream))
        }
    }

    fun getSavedPlace(): Dream {
        val dreamJson = sharedPreferences().getString("dream", "")
        return Gson().fromJson(dreamJson, Dream::class.java)
    }

    fun isDreamSaved() = sharedPreferences().contains("dream")

    private fun sharedPreferences() = DreamApplication.context.
    getSharedPreferences("dream", Context.MODE_PRIVATE)
}
