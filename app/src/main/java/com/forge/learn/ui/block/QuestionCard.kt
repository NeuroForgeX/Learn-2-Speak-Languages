package com.forge.learn.ui.block

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forge.learn.data.Question
import com.forge.learn.isValueMarkedForFuture
import com.forge.learn.markValueForFuture
import com.forge.learn.removeMarkedValueForFuture
import com.forge.learn.ui.view.sampleQuestions

@Composable
fun QuestionCard(modifier: Modifier = Modifier, question: Question, onOptionSelected: (String) -> Unit) {
    val freeze: MutableState<Boolean> = remember { mutableStateOf(false) }
    val options: List<String> = (question.falseOptions + question.answer).shuffled()

    LaunchedEffect(Unit) {
        options.shuffled()
    }

    Card(modifier = modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 150.dp)),
         shape = RoundedCornerShape(16.dp),
         colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
         elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            // Question Text with Heart Icon
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

                var isMarked by remember { mutableStateOf(isValueMarkedForFuture(question.id)) }

                IconButton(onClick = {
                    isMarked = !isMarked
                    if (isMarked) markValueForFuture(question.id) else removeMarkedValueForFuture(question.id)
                }, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = if (isMarked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                         contentDescription = "Mark for future",
                         tint = if (isMarked) Color.Red else Color.Gray,
                         modifier = Modifier.size(24.dp))
                }

                Text(text = question.question,
                     fontSize = 20.sp,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Start,
                     color = MaterialTheme.colorScheme.onSurface,
                     modifier = Modifier
                             .weight(1f)
                             .padding(start = 8.dp))
            }

            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                for (option in options) {
                    OptionButton(option = option, isCorrect = (option == question.answer), onClick = { onOptionSelected(option) }, modifier = Modifier.weight(1f), shouldFreeze = freeze)
                }
            }
        }
    }
}

@Composable
private fun OptionButton(option: String, isCorrect: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, shouldFreeze: MutableState<Boolean>) {
    var isSelected by remember { mutableStateOf(false) }
    val (backgroundColor, contentColor, border) = when {
        !shouldFreeze.value -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, BorderStroke(1.dp, MaterialTheme.colorScheme.outline))
        isCorrect && shouldFreeze.value -> Triple(Color(0xFF81C784), // Light green
                                                  Color(0xFF1B5E20), // Dark green text
                                                  null)

        isSelected && !isCorrect && shouldFreeze.value -> Triple(Color(0xFFEF9A9A), // Light red
                                                                 Color(0xFFB71C1C), // Dark red text
                                                                 null)

        else -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, BorderStroke(1.dp, MaterialTheme.colorScheme.outline))
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = {
            if (!shouldFreeze.value) {
                onClick()
                isSelected = true
                shouldFreeze.value = true
            }
        },
               modifier = modifier
                       .height(80.dp)
                       .clip(shape = RoundedCornerShape(16.dp)),
               colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
               border = border) {
            Text(text = option, fontSize = 16.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
        }
    }
}

@Composable
@Preview
fun QuestionCardPreview() {
    QuestionCard(question = sampleQuestions().first(), onOptionSelected = {})
}
