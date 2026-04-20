package com.forge.learn.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forge.learn.getQuestions
import com.forge.learn.getReviewQuestions
import com.forge.learn.ui.view.WelcomeScreen
import com.forge.learn.ui.view.WordLearnScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome", modifier = Modifier.fillMaxSize()) {
        composable("welcome") {
            WelcomeScreen(navController = navController, innerPadding = innerPadding)
        }
        composable("wordLearn") {
            WordLearnScreen(navController = navController, innerPadding = innerPadding, questions = getQuestions())
        }
        composable("review") {
            WordLearnScreen(navController = navController, innerPadding = innerPadding, questions = getQuestions(getReviewQuestions()))
        }
    }
}
