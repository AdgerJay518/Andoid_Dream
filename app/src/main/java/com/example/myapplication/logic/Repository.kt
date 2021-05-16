package com.example.myapplication.logic


import androidx.lifecycle.liveData
import com.example.myapplication.logic.dao.DreamDao
import com.example.myapplication.logic.model.Dream
import com.example.myapplication.logic.network.DreamNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


object Repository {

    fun saveDream(dream:Dream)=DreamDao.saveDream(dream)
    fun getSavedPlace()=DreamDao.getSavedPlace()
    fun isDreamSaved()=DreamDao.isDreamSaved()

    fun searchDream(q: String) = fire(Dispatchers.IO) {
        val response = DreamNetwork.searchDream(q)
        if (response.reason== "successed") {
            val dream = response.result
            Result.success(dream)
        } else {
            Result.failure(RuntimeException("response status is ${response.error_code}"))
        }
    }

    fun searchDreamDetail(id: String) = fire(Dispatchers.IO) {
        val response = DreamNetwork.searchDreamDetail(id)
        if (response.reason== "查询成功") {
            val dream = response.result
            Result.success(dream)
        } else {
            Result.failure(RuntimeException("response status is ${response.error_code}"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}
