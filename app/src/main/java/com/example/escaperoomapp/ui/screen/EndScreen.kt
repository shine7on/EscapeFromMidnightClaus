package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.escaperoomapp.ui.navigation.StartScreenRoute

@Composable
fun EndScreen(
    backStack: NavBackStack<NavKey>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("You Escaped!")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    backStack.add(StartScreenRoute)
                }
            ) {
                Text("Restart Game")
            }
        }
    }
}
