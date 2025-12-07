package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallLeftScreen(vm: GameViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3E5FC)),
        contentAlignment = Alignment.Center
    ) {
        Text("WALL LEFT (Fireplace / Ornament / Locker)")
    }
}