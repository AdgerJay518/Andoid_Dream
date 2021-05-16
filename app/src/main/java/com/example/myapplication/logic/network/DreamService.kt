package com.example.myapplication.logic.network

import com.example.myapplication.DreamApplication
import com.example.myapplication.logic.model.DreamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DreamService {
    @GET("dream/query?key=${DreamApplication.key}")
    fun searchDream(@Query("q") 	q: String): Call<DreamResponse>
}
