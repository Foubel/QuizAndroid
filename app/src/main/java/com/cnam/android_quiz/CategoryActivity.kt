package com.cnam.android_quiz

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import database.Quiz
import kotlinx.coroutines.launch
import repository.QuizRepository

class CategoryActivity : AppCompatActivity() {

    private lateinit var quizRepository: QuizRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        quizRepository = QuizRepository(Quiz.getInstance(this), this)

        val listView = findViewById<ListView>(R.id.lvCategories)

        lifecycleScope.launch {
            quizRepository.getAllCategories().observe(this@CategoryActivity) { categories ->
                categories?.let {
                    val categoryNames = it.map { category -> category.name }
                    val adapter = ArrayAdapter(this@CategoryActivity, android.R.layout.simple_list_item_1, categoryNames)
                    listView.adapter = adapter

                    listView.setOnItemClickListener { _, _, position, _ ->
                        val category = it[position]
                        val intent = Intent(this@CategoryActivity, QuizActivity::class.java)
                        intent.putExtra("categoryId", category.id)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
