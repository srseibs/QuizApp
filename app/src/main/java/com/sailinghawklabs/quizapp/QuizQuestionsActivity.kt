package com.sailinghawklabs.quizapp

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentQuestionNum: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mNumQuestions = 0
    private var mCurrentSelection = -1
    private var mNumCorrectAnswers = 0
    private var mUserName: String? = null

    private val buttonList: ArrayList<TextView> by lazy {
        arrayListOf(
            tv_choice_1,
            tv_choice_2,
            tv_choice_3,
            tv_choice_4
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.TAG_USER_NAME)

        mQuestionsList = FlagQuiz.getQuestions(this)
        mNumQuestions = mQuestionsList!!.size
        mCurrentQuestionNum = 1
        mNumCorrectAnswers = 0

        Log.d("QuizQuestionsActivity", "onCreate: ${mQuestionsList?.size}")

        setupQuestionUI()
        buttonList.forEach { it.setOnClickListener(this) }

        bt_submit.setOnClickListener(this)
    }

    private fun setupQuestionUI() {
        val question = mQuestionsList!![mCurrentQuestionNum - 1]
        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        bt_submit.text = if (mCurrentSelection == mNumQuestions)  "Finished" else "Submit"
        setButtonStates()
        buttonList.forEachIndexed {index, textView -> textView.text = question.selections[index]}
        tv_progress.text = "$mCurrentQuestionNum/$mNumQuestions"
        progressBar.progress = (100 * mCurrentQuestionNum) / mNumQuestions
    }

    private fun setSelectionPassFail(selection: Int, drawableView: Int) {
        buttonList[selection - 1].background = ContextCompat.getDrawable(this, drawableView)
    }

    private fun setButtonStates(selected: Int = -1) {

        if (selected > 0) {
            mCurrentSelection = selected
        }

        for ((i, button) in buttonList.withIndex()) {
            val buttonNum = i + 1

            // default settings
            button.background = ContextCompat.getDrawable(this, R.drawable.choice_default_bg)
            button.setTextColor(ContextCompat.getColor(this, R.color.lightBluishGray))
            button.typeface = Typeface.DEFAULT


            if (buttonNum == selected) {
                button.background = ContextCompat.getDrawable(this, R.drawable.choice_selected_bg)
                button.setTextColor(ContextCompat.getColor(this, R.color.textDarkGray))
                button.typeface = Typeface.DEFAULT_BOLD
            }

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_choice_1 -> {
                setButtonStates(1)
            }
            R.id.tv_choice_2 -> {
                setButtonStates(2)
            }
            R.id.tv_choice_3 -> {
                setButtonStates(3)
            }
            R.id.tv_choice_4 -> {
                setButtonStates(4)
            }
            R.id.bt_submit -> {
                if (mCurrentSelection < 1) { // user pressed SUBMIT without a selection

                    when {
                        mCurrentQuestionNum <= mNumQuestions -> {
                            setupQuestionUI()
                        }
                        else -> {
                            intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.TAG_USER_NAME, mUserName)
                            intent.putExtra(Constants.TAG_NUM_CORRECT, mNumCorrectAnswers)
                            intent.putExtra(Constants.TAG_NUM_QUESTIONS, mNumQuestions)
                            startActivity(intent)
                            finish()
                        }
                    }

                } else { // user selected something first
                    val question = mQuestionsList?.get(mCurrentQuestionNum - 1)
                    val correctSelection = question!!.correctAnswer + 1
                    if (correctSelection != mCurrentSelection) {
                        setSelectionPassFail(mCurrentSelection, R.drawable.choice_wrong_bg)
                    } else {
                        mNumCorrectAnswers++
                    }
                    setSelectionPassFail(correctSelection!!, R.drawable.choice_correct_bg)

                    if (mCurrentQuestionNum == mNumQuestions) {
                        bt_submit.text = "Finished"
                    } else {
                        bt_submit.text = "Next Question"
                    }
                    mCurrentSelection = 0
                    mCurrentQuestionNum++

                }
            }
        }
    }
}
