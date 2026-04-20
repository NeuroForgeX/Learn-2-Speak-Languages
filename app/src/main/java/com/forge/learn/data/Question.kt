package com.forge.learn.data

data class Question(val id: Int, val question: String, var answer: String = "", val falseOptions: MutableList<String> = ArrayList<String>(3), val defaultAnswer: String)
