package com.cnam.android_quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import database.Quiz
import kotlinx.coroutines.launch
import repository.QuizRepository

class ScoreActivity : AppCompatActivity() {

    private lateinit var quizRepository: QuizRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        quizRepository = QuizRepository(Quiz.getInstance(this), this)

        val listView = findViewById<ListView>(R.id.lvScores)

        lifecycleScope.launch {
            val scores = quizRepository.getAllScores()
            if (scores.isNotEmpty()) {
                val scoreAdapter = ScoreAdapter(this@ScoreActivity, this@ScoreActivity, scores)
                listView.adapter = scoreAdapter
            } else {
                // Afficher un message ou une vue indiquant qu'il n'y a pas de scores à afficher
            }
        }

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btnDeleteScores).setOnClickListener {
            lifecycleScope.launch {
                quizRepository.deleteAllScores()
                // Actualiser l'affichage après la suppression des scores
                val scores = quizRepository.getAllScores()
                if (scores.isNotEmpty()) {
                    val scoreAdapter = ScoreAdapter(this@ScoreActivity, this@ScoreActivity, scores)
                    listView.adapter = scoreAdapter
                } else {
                    // Afficher un message ou une vue indiquant qu'il n'y a pas de scores à afficher
                }
            }
        }
    }
}
