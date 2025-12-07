package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.escaperoomapp.ui.navigation.GameScreenRoute

@Composable
fun StartScreen(
    backStack: NavBackStack<NavKey>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                backStack.add(GameScreenRoute)
            }
        ) {
            Text("Start Game")
        }
    }
}

