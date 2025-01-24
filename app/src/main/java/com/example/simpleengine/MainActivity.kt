package com.example.simpleengine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen()
        }
    }
}

@Composable
fun AppScreen() {
    var isModalVisible by remember { mutableStateOf(false) }
    var isVideoOn by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("Home") }
    var lastEvent by remember { mutableStateOf("No Event") }
    var inputNumber by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Universal Rules",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { isModalVisible = !isModalVisible }) {
                    Text(text = if (isModalVisible) "Modal ON" else "Modal OFF")
                }

                Button(onClick = { isVideoOn = !isVideoOn }) {
                    Text(text = if (isVideoOn) "Video ON" else "Video OFF")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Events",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    lastEvent = "app_visit: $inputNumber"
                    trackEvent("app_visit", inputNumber)
                }) {
                    Text(textAlign = TextAlign.Center, text = "Trigger\napp_visit")
                }

                Button(onClick = {
                    lastEvent = "scroll_articles: $inputNumber"
                    trackEvent("scroll_articles", inputNumber)
                }) {
                    Text(textAlign = TextAlign.Center, text = "Trigger\nscroll_articles")
                }


            }

            OutlinedTextField(
                value = inputNumber,
                onValueChange = { input ->
                    if (input.all { it.isDigit() }) inputNumber = input
                },
                label = { Text("Enter a number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Navigation",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                Button(onClick = { currentScreen = "ScreenOne" }) {
                    Text(text = "ScreenOne")
                }
                Button(onClick = { currentScreen = "ScreenTwo" }) {
                    Text(text = "ScreenTwo")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { currentScreen = "ScreenThree" }) {
                    Text(text = "ScreenThree")
                }
                Button(onClick = { currentScreen = "ScreenFour" }) {
                    Text(text = "ScreenFour")
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Last Event: $lastEvent",
                style = MaterialTheme.typography.bodyLarge,

            )

            Text(
                text = "Current Screen: $currentScreen",
                style = MaterialTheme.typography.bodyLarge,
            )
        }

    }
}

fun trackEvent(event: String, value: String) {

}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    AppScreen()
}