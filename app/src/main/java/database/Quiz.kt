package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dao.CategoryDao
import dao.QuestionDao
import dao.ScoreDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Database(entities = [Category::class, Question::class, Score::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Quiz : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: Quiz? = null

        fun getInstance(context: Context): Quiz {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Quiz::class.java,
                    "quiz_database"
                )
                    .addCallback(QuizDatabaseCallback(context, CoroutineScope(SupervisorJob())))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
