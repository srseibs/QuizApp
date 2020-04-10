package com.sailinghawklabs.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // make the view cover the Notification area too
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val username = intent.getStringExtra(Constants.TAG_USER_NAME)
        tv_username.text = username

        val numCorrect = intent.getIntExtra(Constants.TAG_NUM_CORRECT, -1)
        val numQuestions = intent.getIntExtra(Constants.TAG_NUM_QUESTIONS, -1)
        tv_score.text = "Your score was $numCorrect out of $numQuestions"

        bt_done.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}
