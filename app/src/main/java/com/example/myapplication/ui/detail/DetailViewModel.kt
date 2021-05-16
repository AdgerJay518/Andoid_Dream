package com.example.myapplication.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication.logic.Repository

class DetailViewModel : ViewModel(){
    private val detailLiveData = MutableLiveData<String>()

    var DreamId=""

    val liveData = Transformations.switchMap(detailLiveData) { id ->
        Repository.searchDreamDetail(id)
    }

    fun refreshDetail(id: String) {
        detailLiveData.value = id
    }
}
