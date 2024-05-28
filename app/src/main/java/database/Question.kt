package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "questions")
@TypeConverters(Converters::class)
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "choices") val choices: List<String>,
    @ColumnInfo(name = "correct_answer") val correctAnswer: Int,
    @ColumnInfo(name = "category_id") val categoryId: Int
)
