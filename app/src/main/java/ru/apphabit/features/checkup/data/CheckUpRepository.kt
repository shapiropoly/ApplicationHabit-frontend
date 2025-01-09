package ru.apphabit.features.checkup.data

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.apphabit.features.checkup.model.CheckUp
import java.time.LocalDate

interface CheckUpRepository {
    suspend fun getAllCheckUps(): List<CheckUp>
    suspend fun addCheckUp(checkup: CheckUp, userToHabitId: Int): CheckUp
    suspend fun getCheckUpsByUserId(userId: Int): CheckUp
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

    override suspend fun addCheckUp(checkup: CheckUp, userToHabitId: Int): CheckUp {
        val body = service.addCheckUp(checkup, userToHabitId).body()!!
        return body
    }

    override suspend fun getCheckUpsByUserId(userId: Int): CheckUp {
        val body = service.getCheckUpsByUserId(userId).body()!!
        return body
    }

    override fun updateCheckUp(id: Int, checkup: CheckUp): Call<CheckUp> {
        return service.updateCheckUp(id, checkup)
    }

    override fun deleteCheckUp(id: Int): Call<CheckUp> {
        return service.deleteCheckUp(id)
    }

}