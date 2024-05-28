package com.cnam.android_quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import database.Question
import database.Quiz
import database.Score
import kotlinx.coroutines.launch
import repository.QuizRepository

class QuizActivity : AppCompatActivity() {

    private lateinit var quizRepository: QuizRepository
    private var questions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var countDownTimer: CountDownTimer? = null
    private var isQuizFinished = false

    private lateinit var tvQuestion: TextView
    private lateinit var rgAnswers: RadioGroup
    private lateinit var tvCorrectAnswers: TextView
    private lateinit var tvWrongAnswers: TextView
    private lateinit var tvCountdown: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        tvQuestion = findViewById(R.id.tvQuestion)
        rgAnswers = findViewById(R.id.rgAnswers)
        tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers)
        tvWrongAnswers = findViewById(R.id.tvWrongAnswers)
        tvCountdown = findViewById(R.id.tvCountdown)

        val categoryId = intent.getIntExtra("categoryId", -1)

        quizRepository = QuizRepository(Quiz.getInstance(this), this)

        lifecycleScope.launch {
            questions = quizRepository.getTenRandomQuestionsByCategory(categoryId)
            if (savedInstanceState == null) {
                showNextQuestion()
            }
        }
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            tvQuestion.text = question.text
            rgAnswers.removeAllViews()
            for ((index, choice) in question.choices.withIndex()) {
                val radioButton = RadioButton(this)
                radioButton.text = choice
                radioButton.id = index
                radioButton.setOnClickListener { validateAnswer(index, question.correctAnswer) }
                rgAnswers.addView(radioButton)
            }
            tvCorrectAnswers.text = "${getString(R.string.correct_answers)} $correctAnswers | "
            tvWrongAnswers.text = "${getString(R.string.wrong_answers)} $wrongAnswers"
            startCountDownTimer()
        } else {
            finishQuiz()
        }
    }

    private fun validateAnswer(selectedAnswer: Int, correctAnswer: Int) {
        countDownTimer?.cancel()
        if (selectedAnswer == correctAnswer) {
            correctAnswers++
        } else {
            wrongAnswers++
        }
        currentQuestionIndex++
        showNextQuestion()
    }

    private fun startCountDownTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvCountdown.text = "${getString(R.string.time_remaining)} ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                wrongAnswers++
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    showNextQuestion()
                } else {
                    finishQuiz()
                }
            }
        }.start()
    }

    private fun finishQuiz() {
        if (!isQuizFinished) {
            isQuizFinished = true
            val username = quizRepository.getUsername()
            val categoryId = intent.getIntExtra("categoryId", -1)

            lifecycleScope.launch {
                quizRepository.insertScore(Score(username = username, categoryId = categoryId, correctAnswers = correctAnswers, wrongAnswers = wrongAnswers))
            }

            countDownTimer?.cancel()

            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("quizFinished", true)
            startActivity(intent)

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
