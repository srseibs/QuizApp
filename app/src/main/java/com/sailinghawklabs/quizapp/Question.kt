package com.sailinghawklabs.quizapp

data class Question (
    val id: Int,
    val question: String,
    val image: Int,
    val selections: List<String>,
    val correctAnswer: Int
)