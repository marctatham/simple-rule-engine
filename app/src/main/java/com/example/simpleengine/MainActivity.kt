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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpleengine.candybar.CandyBarDecision
import com.example.simpleengine.candybar.triggers.TriggerEvent

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
    var inputNumber by remember { mutableStateOf("0") }

    val candyBarDecision = candyBarManager.state.collectAsState(CandyBarDecision(false))

    val screenChangeHandler: (String) -> Unit = {
        currentScreen = it
        screenTracker.track(it)
    }

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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    isModalVisible = !isModalVisible
                    modalTracker.track(isModalVisible)
                }) {
                    Text(text = if (isModalVisible) "Modal ON" else "Modal OFF")
                }

                Button(onClick = {
                    isVideoOn = !isVideoOn
                    mediaTracker.track(isVideoOn)
                }) {
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    lastEvent = "app_visit: $inputNumber"

                    eventTracker.track(TriggerEvent.AppVisitEvent(inputNumber.toInt()))
                }) {
                    Text(textAlign = TextAlign.Center, text = "Trigger\napp_visit")
                }

                Button(onClick = {
                    lastEvent = "scroll_articles: $inputNumber"
                    eventTracker.track(TriggerEvent.AppVisitTimeEvent(inputNumber.toInt()))
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

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Navigation",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    val screen = "ScreenOne"
                    screenChangeHandler(screen)
                }) {
                    Text(text = "ScreenOne")
                }
                Button(onClick = {
                    val screen = "ScreenTwo"
                    screenChangeHandler(screen)
                }) {
                    Text(text = "ScreenTwo")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    val screen = "ScreenThree"
                    screenChangeHandler(screen)
                }) {
                    Text(text = "ScreenThree")
                }
                Button(onClick = {
                    val screen = "ScreenFour"
                    screenChangeHandler(screen)
                }) {
                    Text(text = "ScreenFour")
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CandyBar Decision: ${candyBarDecision.value.show}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

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

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    AppScreen()
}