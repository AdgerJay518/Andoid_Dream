package com.example.myapplication.ui.dream

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication.logic.Repository
import com.example.myapplication.logic.dao.DreamDao
import com.example.myapplication.logic.model.Dream


class DreamViewModel : ViewModel() {

    fun saveDream(dream:Dream)= DreamDao.saveDream(dream)
    fun getSavedPlace()= DreamDao.getSavedPlace()
    fun isDreamSaved()= DreamDao.isDreamSaved()

    private val searchLiveData = MutableLiveData<String>()
    val dreamList = ArrayList<Dream>()
    val liveData = Transformations.switchMap(searchLiveData) { q ->
        Repository.searchDream(q)
    }

    fun searchDream(q: String) {
        searchLiveData.value = q
    }
}
