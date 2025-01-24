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

// TODO: State management on a per-campaign basis (clearing state if the campaign changes)
// TODO: Delay of enacting the candybar

//  What do we have so far:
//  - easily extensible
//  - easy to test

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen()
        }
    }
}


const val screenOne = "ScreenOne"
const val screenTwo = "ScreenTwo"
const val screenThree = "ScreenThree"
const val screenFour = "ScreenFour"

@Composable
fun AppScreen() {


    var isModalVisible by remember { mutableStateOf(false) }
    var isMediaPlaying by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf(screenOne) }
    val lastEvent: TriggerEvent? by eventStore.observeEvents().collectAsState(null)

    var inputNumber by remember { mutableStateOf("0") }

    val candyBarDecision: CandyBarDecision by candyBarManager.state.collectAsState(CandyBarDecision(false, campaign))

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
                    isMediaPlaying = !isMediaPlaying
                    mediaTracker.track(isMediaPlaying)
                }) {
                    Text(text = if (isMediaPlaying) "Video ON" else "Video OFF")
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
                    eventTracker.track(TriggerEvent.AppVisitEvent(inputNumber.toInt()))
                }) {
                    Text(textAlign = TextAlign.Center, text = "Trigger\napp_visit")
                }

                Button(onClick = {
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
                    val screen = screenOne
                    screenChangeHandler(screen)
                }) {
                    Text(text = screenOne)
                }
                Button(onClick = {
                    val screen = screenTwo
                    screenChangeHandler(screen)
                }) {
                    Text(text = screenTwo)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    val screen = screenThree
                    screenChangeHandler(screen)
                }) {
                    Text(text = screenThree)
                }
                Button(onClick = {
                    val screen = screenFour
                    screenChangeHandler(screen)
                }) {
                    Text(text = screenFour)
                }
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(0.5F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Key Metrics:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Stat("Campaign:", candyBarDecision.config?.title ?: "")
            Stat("Modals:", "$isModalVisible")
            Stat("Media:", "$isMediaPlaying")
            Stat("Screen:", currentScreen)
            Stat("Event:", "${lastEvent?.toDisplayName()}")

            Result(candyBarDecision)
        }

    }
}

@Composable
fun Stat(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun Result(result: CandyBarDecision) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Show CandyBar:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = result.show.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    AppScreen()
}

fun TriggerEvent?.toDisplayName():String {
    return when (this) {
        is TriggerEvent.AppVisitEvent -> "App Visit: ${this.visitCount}"
        is TriggerEvent.AppVisitTimeEvent -> "App Duration: ${this.durationInMinutes}"
        else -> "none"
    }
}