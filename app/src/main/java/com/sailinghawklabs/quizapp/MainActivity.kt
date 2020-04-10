package com.sailinghawklabs.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // make the view cover the Notification area too
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btn_start.setOnClickListener {
            if (et_name.text.isNullOrEmpty()) {
                dismissKeyboard()
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.TAG_USER_NAME, et_name.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }


    private fun dismissKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_name.windowToken, 0)
    }
}
