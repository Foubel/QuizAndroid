package dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import database.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE category_id = :categoryId")
    suspend fun getQuestionsByCategory(categoryId: Int): List<Question>

    @Insert
    suspend fun insertQuestion(question: Question)

    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()

}
