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
import ru.apphabit.features.habits.data.HabitRepository
import ru.apphabit.features.habits.model.Habit

class CollectionsVM (
    private val repositoryCollections: CollectionRepository,
    private val repositoryHabits: HabitRepository
) : ViewModel() {

    private val _collections = MutableLiveData<List<Collection>>()
    val collections: LiveData<List<Collection>> get() = _collections

    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> get() = _habits

    private val _collection = MutableLiveData<Collection>()
    val collection: LiveData<Collection> get() = _collection


    init {
        getAllCollections()
    }

    fun getAllCollections() {
        viewModelScope.launch {
            val collectionsList = repositoryCollections.getAllCollections()
            Log.d("CollectionsVM", "Collections received: $collectionsList")
            _collections.value = collectionsList
        }
    }

    fun addCollection(collection: Collection) {
        viewModelScope.launch {
            val newCollection = repositoryCollections.addCollection(collection)
            Log.d("Add collection from CollectionsVM", "Collection: $collection")
            _collection.value = newCollection
        }
    }

    fun getCollectionById(id: Int) {
        viewModelScope.launch {
            _collection.value = repositoryCollections.getCollectionById(id)
        }
    }

    fun getHabitsByCollectionId(collectionId: Int) {
        viewModelScope.launch {
            _habits.value = repositoryHabits.getHabitsByCollectionId(collectionId)
        }
    }

    fun updateCollection(id: Int, collection: Collection, habitsList: List<Habit>) {
        repositoryCollections.updateCollection(id, collection).enqueue(object : Callback<Collection> {
            override fun onResponse(call: Call<Collection>, response: Response<Collection>) {
                // handle the response
            }

            override fun onFailure(call: Call<Collection>, t: Throwable) {
                // handle the failure
            }
        })
    }

    fun deleteCollection(id: Int) {
        repositoryCollections.deleteCollection(id).enqueue(object : Callback<Collection> {
            override fun onResponse(call: Call<Collection>, response: Response<Collection>) {
                // handle the response
            }

            override fun onFailure(call: Call<Collection>, t: Throwable) {
                // handle the failure
            }
        })
    }
}