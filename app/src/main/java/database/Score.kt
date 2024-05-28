package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "correct_answers") val correctAnswers: Int,
    @ColumnInfo(name = "wrong_answers") val wrongAnswers: Int
) : Comparable<Score> {
    override fun compareTo(other: Score): Int {
        return other.correctAnswers.compareTo(this.correctAnswers)
    }
}


