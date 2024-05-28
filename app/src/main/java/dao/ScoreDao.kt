package dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import database.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY correct_answers DESC")
    suspend fun getAllScores(): List<Score>

    @Insert
    suspend fun insertScore(score: Score)

    @Query("DELETE FROM scores")
    suspend fun deleteAllScores()

}