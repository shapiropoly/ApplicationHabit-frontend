package ru.apphabit.features.checkup.data

import android.util.Log
import retrofit2.Call
import ru.apphabit.features.checkup.model.CheckUp

interface CheckUpRepository {
    suspend fun getAllCheckUps(): List<CheckUp>
    suspend fun addCheckUp(checkup: CheckUp): CheckUp
    suspend fun getCheckUpById(id: Int): CheckUp
    fun updateCheckUp(id: Int, checkup: CheckUp): Call<CheckUp>
    fun deleteCheckUp(id: Int): Call<CheckUp>
}

class CheckUpRepositoryImpl (private val service: CheckUpApiService): CheckUpRepository {
    override suspend fun getAllCheckUps(): List<CheckUp> {
        val response = service.getAllCheckUps()
        if (response.isSuccessful) {
            val body = response.body()
            Log.d("checkup", "Response: $body")
            return body ?: emptyList()
        } else {
            Log.e("checkup", "Error: ${response.errorBody()?.string()}")
            return emptyList()
        }
    }

    override suspend fun addCheckUp(checkup: CheckUp): CheckUp {
        val body = service.addCheckUp(checkup).body()!!
        return body
    }

    override suspend fun getCheckUpById(id: Int): CheckUp {
        val body = service.getCheckUpById(id).body()!!
        return body
    }
    override fun updateCheckUp(id: Int, checkup: CheckUp): Call<CheckUp> {
        return service.updateCheckUp(id, checkup)
    }

    override fun deleteCheckUp(id: Int): Call<CheckUp> {
        return service.deleteCheckUp(id)
    }

}