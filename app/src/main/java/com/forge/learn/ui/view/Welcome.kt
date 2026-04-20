package com.forge.learn.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WelcomeScreen(navController: NavController, innerPadding: PaddingValues) {
    Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Learn 2 Speak Languages", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 48.dp))

        Button(onClick = { navController.navigate("wordLearn") },
               modifier = Modifier
                       .fillMaxWidth()
                       .height(56.dp)
                       .padding(vertical = 8.dp),
               shape = RoundedCornerShape(12.dp),
               colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
            Text(text = "Learn 1", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(onClick = { /* Navigate to Game 1 */ },
               modifier = Modifier
                       .fillMaxWidth()
                       .height(56.dp)
                       .padding(vertical = 8.dp),
               shape = RoundedCornerShape(12.dp),
               colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
            Text(text = "Game 1", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(onClick = { /* Navigate to Statistics */ }, modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 8.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Statistics", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(onClick = { navController.navigate("review") }, modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 8.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Review", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Button(onClick = { /* Navigate to Settings */ }, modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 8.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Settings", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
@Preview
fun WelcomePreview() {
    WelcomeScreen(navController = rememberNavController(), innerPadding = PaddingValues())
}

