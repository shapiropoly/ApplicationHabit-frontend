package ru.apphabit.features.checkup.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.apphabit.features.checkup.data.CheckUpRepository
import ru.apphabit.features.checkup.model.CheckUp

class CheckUpVM (private val repository: CheckUpRepository) : ViewModel() {

    private val _checkup = MutableLiveData<List<CheckUp>>()
    val checkup: LiveData<List<CheckUp>> get() = _checkup

    init {
        getAllCheckUp()
    }

    fun getAllCheckUp() {
        viewModelScope.launch {
            val checkupList = repository.getAllCheckUps()
            _checkup.value = checkupList
        }
    }

    fun updateCheckUp(id: Int, checkup: CheckUp) {
        repository.updateCheckUp(id, checkup).enqueue(object : Callback<CheckUp> {
            override fun onResponse(call: Call<CheckUp>, response: Response<CheckUp>) {
                // handle the response
            }

            override fun onFailure(call: Call<CheckUp>, t: Throwable) {
                // handle the failure
            }
        })
    }

    fun deleteCheckUp(id: Int) {
        repository.deleteCheckUp(id).enqueue(object : Callback<CheckUp> {
            override fun onResponse(call: Call<CheckUp>, response: Response<CheckUp>) {
                // handle the response
            }

            override fun onFailure(call: Call<CheckUp>, t: Throwable) {
                // handle the failure
            }
        })
    }
}