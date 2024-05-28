package com.cnam.android_quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import database.Quiz
import database.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import repository.QuizRepository

class ScoreAdapter(private val lifecycleOwner: LifecycleOwner, private val context: Context, private val scores: List<Score>) :
    ArrayAdapter<Score>(context, android.R.layout.simple_list_item_1, scores) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val score = scores[position]

        val textView = view.findViewById<TextView>(android.R.id.text1)
        val totalScore = score.correctAnswers

        lifecycleOwner.lifecycleScope.launch {
            val categoryName = withContext(Dispatchers.IO) {
                val quizRepository = QuizRepository(Quiz.getInstance(context), context)
                quizRepository.getCategoryById(score.categoryId)?.name ?: ""
            }
            textView.text = "${score.username} - Category: $categoryName - Total Score: $totalScore"
        }

        return view
    }
}
