package repository

import android.content.Context
import androidx.lifecycle.LiveData
import database.Category
import database.Question
import database.Quiz
import database.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRepository(private val database: Quiz, private val context: Context) {
    suspend fun getQuestionsByCategory(categoryId: Int): List<Question> =
        withContext(Dispatchers.IO) {
            database.questionDao().getQuestionsByCategory(categoryId)
        }

    fun getAllCategories(): LiveData<List<Category>> = database.categoryDao().getAllCategories()

    suspend fun getAllScores(): List<Score> = withContext(Dispatchers.IO) {
        database.scoreDao().getAllScores()
    }

    fun getUsername(): String {
        // Cette opération n'est pas longue, vous pouvez la laisser sur le thread principal
        val sharedPreferences = context.getSharedPreferences("quiz_scores", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", "") ?: ""
    }

    suspend fun insertScore(score: Score) = withContext(Dispatchers.IO) {
        database.scoreDao().insertScore(score)
    }

    suspend fun getCategoryById(categoryId: Int): Category? = withContext(Dispatchers.IO) {
        database.categoryDao().getCategoryById(categoryId)
    }

    suspend fun deleteAllScores() = withContext(Dispatchers.IO) {
        database.scoreDao().deleteAllScores()

    }

    suspend fun getTenRandomQuestionsByCategory(categoryId: Int): List<Question> = withContext(Dispatchers.IO) {
        val allQuestions = database.questionDao().getQuestionsByCategory(categoryId)
        if (allQuestions.size <= 10) {
            allQuestions // Retourne toutes les questions si moins de 10
        } else {
            allQuestions.shuffled().take(10) // Prend 10 questions aléatoires
        }
    }
}