package com.example.myapplication.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object DreamNetwork {
    private val dreamService = ServiceCreator.create(DreamService::class.java)
    private val detailService = ServiceCreator.create(DetailService::class.java)
    suspend fun searchDream(q: String) = dreamService.searchDream(q).await()
    suspend fun searchDreamDetail(id: String) = detailService.searchDreamDetail(id).await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
