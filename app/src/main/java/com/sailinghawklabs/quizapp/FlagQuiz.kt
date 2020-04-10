package com.sailinghawklabs.quizapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import java.util.*
import kotlin.collections.ArrayList

// extension function to look up a resource by string name
fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

object Constants {
    const val TAG_USER_NAME = "user_name"
    const val TAG_NUM_QUESTIONS = "num_questions"
    const val TAG_NUM_CORRECT = "num_correct"
}

object FlagQuiz {
    private const val NUM_GUESSES = 4
    const val QUESTION = "What country does this flag belong to?"
    private val countries: ArrayList<String> = arrayListOf(
        "Argentina",
        "Australia",
        "Belgium",
        "Brazil",
        "Denmark",
        "Fiji",
        "Germany",
        "India",
        "Kuwait",
        "New Zealand"
    )

    fun getQuestions(context: Context): ArrayList<Question> {
        countries.shuffle()
        val quiz = ArrayList<Question>()
        for ((i, country) in countries.withIndex()) {
            val guessIndexList = genGuesses(i)
            quiz.add(
                Question(
                    i,
                    QUESTION,
                    flagFileId(context, country),
                    countries.slice(guessIndexList),
                    guessIndexList.indexOf(i)
                )
            )
        }
        return quiz
    }

    // master index list contains the guesses in {0.... (numCountries-1)}
    private val masterIndexList = List<Int>(countries.size) { i -> i }

    // returns random set of NUM_GUESSES that contains the correct guess (also at random position)
    private fun genGuesses(correct: Int): List<Int> {
        val generator = ArrayList<Int>()
        generator.addAll(masterIndexList)
        generator.remove(correct)
        generator.shuffle()
        generator.add(Random().nextInt(NUM_GUESSES), correct)
        return generator.subList(0, NUM_GUESSES)
    }

    // returns the Int value of the "ic_flag_of_xxx" for the passed country string
    @SuppressLint("DefaultLocale")
    private fun flagFileId(context: Context, country: String): Int {
        val fileName = "ic_flag_of_" +country.toLowerCase().replace(" ", "_")
        return context.resIdByName(fileName, "drawable")
    }
}