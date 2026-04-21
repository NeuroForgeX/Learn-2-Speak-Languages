package com.forge.learn

import android.content.Context
import com.forge.learn.data.Question

private lateinit var questions: List<Question>

fun getQuestions(): List<Question> {
    return questions
}

fun getQuestions(vararg ids: Int): List<Question> {
    return questions.filter { it.id in ids }
}

fun getQuestions(ids: Collection<Int>): List<Question> {
    return questions.filter { it.id in ids }
}

fun loadData(context: Context) {

    val answers = ArrayList<String>()
    val questionsList = ArrayList<Question>()
    try {
        context.assets.open("Questions.csv").bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val tokens = line.split(";")
                val answer = tokens[2]
                val question = Question(id = tokens[0].toInt(), question = tokens[1], answer = answer, defaultAnswer = answer)
                questionsList.add(question)
                answers.add(answer)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    for (question in questionsList) {
        val wrong = answers.shuffled().take(4).filter { it != question.defaultAnswer }.take(3)
        question.falseOptions.addAll(wrong)
//        translateGermanToEnglish(question.defaultAnswer, onResult = { result ->
//            result.isNotEmpty().let {
//                question.answer = result
//            }
//        })
    }

    questions = ArrayList<Question>(questionsList.shuffled())
}
