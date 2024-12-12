package ru.apphabit.features.collections.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.apphabit.features.collections.data.CollectionRepository
import ru.apphabit.features.collections.model.Collection

class CollectionsVM (private val repository: CollectionRepository) : ViewModel(){
    private val _collections = MutableLiveData<List<Collection>>()
    val collections: LiveData<List<Collection>> get() = _collections

    init {
        getAllCollections()
    }

    fun getAllCollections() {
        viewModelScope.launch {
            val collectionsList = repository.getAllCollections()
            Log.d("CollectionsVM", "Collections received: $collectionsList")
            _collections.value = collectionsList
        }
    }


    fun updateCollection(id: Int, habit: Collection) {
        repository.updateCollection(id, habit).enqueue(object : Callback<Collection> {
            override fun onResponse(call: Call<Collection>, response: Response<Collection>) {
                // handle the response
            }

            override fun onFailure(call: Call<Collection>, t: Throwable) {
                // handle the failure
            }
        })
    }

    fun deleteCollection(id: Int) {
        repository.deleteCollection(id).enqueue(object : Callback<Collection> {
            override fun onResponse(call: Call<Collection>, response: Response<Collection>) {
                // handle the response
            }

            override fun onFailure(call: Call<Collection>, t: Throwable) {
                // handle the failure
            }
        })
    }
}