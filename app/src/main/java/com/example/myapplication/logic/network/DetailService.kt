package com.example.myapplication.logic.network

import com.example.myapplication.DreamApplication
import com.example.myapplication.logic.model.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailService {
    @GET("dream/queryid?key=${DreamApplication.key}")
    fun searchDreamDetail(@Query("id") 	id: String): Call<DetailResponse>
}
