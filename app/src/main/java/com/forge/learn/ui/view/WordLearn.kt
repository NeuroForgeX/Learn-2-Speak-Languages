package com.forge.learn.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.forge.learn.data.Question
import com.forge.learn.markValueErrored
import com.forge.learn.ui.block.QuestionCard
import com.forge.learn.valuesMarkedForFuture
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun WordLearnScreen(navController: NavController, innerPadding: PaddingValues, questions: List<Question>) {
    // Sample questions - replace with actual data
    val coroutineScope = rememberCoroutineScope()
    val markedForFuture = remember { valuesMarkedForFuture }
    var shuffledQuestions by remember { mutableStateOf(questions) }
    val pagerState = rememberPagerState(pageCount = { shuffledQuestions.size })

    Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)) {
        // Top bar with icons
        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            // Exit icon
            IconButton(onClick = { navController.navigate("welcome") }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Exit", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(28.dp))
            }

            // Right side icons
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Reload/Shuffle icon
                IconButton(onClick = {
                    shuffledQuestions = shuffledQuestions.take(pagerState.currentPage) + shuffledQuestions.subList(pagerState.currentPage, pagerState.pageCount)
                            .shuffled(Random(System.currentTimeMillis()))
                }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Shuffle Questions", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(24.dp))
                }

                // Love/Favorite icon with count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.Red, modifier = Modifier.size(24.dp))
                    }
                    Text(text = "${markedForFuture.size}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 4.dp))
                }

            }
        }

        // Question progress indicator
        Text(text = "Question ${pagerState.currentPage + 1} of ${shuffledQuestions.size}",
             fontSize = 14.sp,
             color = MaterialTheme.colorScheme.onSurfaceVariant,
             modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 16.dp, vertical = 8.dp))

        // Linear progress indicator
        LinearProgressIndicator(
            progress = { (pagerState.currentPage + 1).toFloat() / shuffledQuestions.size },
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                               )

        // Question Cards
        HorizontalPager(state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) { page ->
            val question = shuffledQuestions[page]
            QuestionCard(question = question, onOptionSelected = { option ->
                // Handle option selection
                if (option != question.answer) {
                    markValueErrored(question.id)
                }
            })
        }

        // Navigation icons
        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            // Left arrow - scroll to previous question
            IconButton(onClick = {
                if (pagerState.currentPage > 0) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            }, enabled = pagerState.currentPage > 0) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                     contentDescription = "Previous Question",
                     tint = if (pagerState.currentPage > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                     modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.width(48.dp))

            // Right arrow - scroll to next question
            IconButton(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }, enabled = pagerState.currentPage < shuffledQuestions.size - 1) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                     contentDescription = "Next Question",
                     tint = if (pagerState.currentPage < shuffledQuestions.size - 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                     modifier = Modifier.size(32.dp))
            }
        }
    }
}

fun sampleQuestions(): List<Question> {
    val q1 = Question(1, question = "Verwirrt", defaultAnswer = "Confused")
    q1.falseOptions.add("Excited")
    q1.falseOptions.add("Tired")
    q1.falseOptions.add("Energetic")
    q1.falseOptions.add("Recoiled")
    q1.answer = q1.defaultAnswer
    val questions: List<Question> = listOf(q1)
    return questions
}

@Composable
@Preview
fun WordLearnPreview() {
    WordLearnScreen(navController = rememberNavController(), questions = sampleQuestions(), innerPadding = PaddingValues())
}
