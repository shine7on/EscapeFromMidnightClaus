package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallRightScreen(vm: GameViewModel) {
    val state = vm.gameState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ===== WINDOW =====
        ClickBox(
            title = if (state.flags.windowOpened) "WINDOW (open)" else "WINDOW (closed)"
        ) { vm.interact(ObjectID.WR_WINDOW) }

        Spacer(Modifier.height(20.dp))

        // ===== PRESENT BOX =====
        ClickBox(
            title = when {
                state.flags.presentOpened -> "PRESENT (opened)"
                state.flags.lockerOpened -> "PRESENT (cuttable)"
                else -> "PRESENT (locked)"
            }
        ) { vm.interact(ObjectID.WR_PRESENT_BOX) }

        Spacer(Modifier.height(20.dp))

        // ===== TREE =====
        ClickBox(
            title = if (state.flags.ornamentShelfOrdered) "TREE (active)" else "TREE (inactive)"
        ) { vm.interact(ObjectID.WR_TREE) }
    }
}

@Composable
fun ClickBox(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF333333)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(title, color = Color.White)
        }
    }
}
