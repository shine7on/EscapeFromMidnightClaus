package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallCenterScreen(vm: GameViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4)),
        contentAlignment = Alignment.Center
    ) {
        Text("WALL CENTER (Door / Wreath / Dot Panel)")
    }
}
