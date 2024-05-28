package dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import database.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): Category?

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

}
