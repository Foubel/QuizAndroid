package com.cnam.android_quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            if (username.isNotEmpty()) {
                saveUsername(username)
                startActivity(Intent(this, CategoryActivity::class.java))
                finish()
            } else {
                etUsername.error = "Please enter a username"
            }
        }
    }

    private fun saveUsername(username: String) {
        val sharedPref = getSharedPreferences("quiz_scores", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            apply()
        }
    }
}
