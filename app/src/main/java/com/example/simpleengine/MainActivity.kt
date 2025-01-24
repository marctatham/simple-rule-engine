package com.example.simpleengine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    var showModal by remember { mutableStateOf(false) }
    var videoOn by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("Home") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showModal = !showModal }) {
                Text(text = if (showModal) "Modal ON" else "Modal OFF")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { videoOn = !videoOn }) {
                Text(text = if (videoOn) "Video ON" else "Video OFF")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { trackEvent() }) {
                Text(text = "Trigger Event")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                currentScreen = if (currentScreen == "Home") "No Show Screen" else "Home"
            }) {
                Text(text = "${if (currentScreen == "Home") "Available" else "No Show"} Screen")
            }
        }

        Text(
            text = "Current Screen: $currentScreen",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

fun trackEvent() {
    println("Event triggered")
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    AppScreen()
}