package ru.apphabit.features.habits.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.apphabit.features.habits.data.CategoryRepository
import ru.apphabit.features.habits.model.Category

class CategoriesVM (private val repository: CategoryRepository) : ViewModel() {

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> get() = _category

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            val categoriesList = repository.getAllCategories()
            Log.d("CategoriesVM", "Categories received: $categoriesList")
            _categories.value = categoriesList
        }
    }

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            _category.value = repository.getCategoryById(id)
        }
    }

    fun updateCategory(id: Int, category: Category) {
        repository.updateCategory(id, category).enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                if (response.isSuccessful) {
                    Log.d("CategoriesVM", "Category updated")
                } else {
                    Log.e("CategoriesVM", "Failed to update category")
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.e("CategoriesVM", "Error updating category", t)
            }
        })
    }

    fun deleteCategory(id: Int) {
        repository.deleteCategory(id).enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                if (response.isSuccessful) {
                    Log.d("CategoriesVM", "Category updated")
                } else {
                    Log.e("CategoriesVM", "Failed to update category")
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                Log.e("CategoriesVM", "Error updating category", t)
            }
        })
    }
}