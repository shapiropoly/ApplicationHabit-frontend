package ru.apphabit.features.habits.data

import retrofit2.Call
import ru.apphabit.features.habits.model.Category

interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoryById(id: Int): Category
    suspend fun addCategory(category: Category): Category
    fun updateCategory(id: Int, category: Category): Call<Category>
    fun deleteCategory(id:Int): Call<Category>
}

class CategoryRepositoryImpl(private val service: CategoryApiService): CategoryRepository {

    override suspend fun getAllCategories(): List<Category> {
        val response = service.getAllCategories()
        if (response.isSuccessful) {
            val body = response.body()
            return body ?: emptyList()
        } else {
            return emptyList()
        }
    }

    override suspend fun addCategory(category: Category) : Category {
        val body = service.addCategory(category).body()!!
        return body
    }

    override suspend fun getCategoryById(id: Int): Category {
        val body = service.getCategoryById(id).body()!!
        return body
    }

    override fun updateCategory(id: Int, category: Category) : Call<Category> {
        return service.updateCategory(id, category)
    }

    override fun deleteCategory(id: Int): Call<Category> {
        return service.deleteCategory(id)
    }
}