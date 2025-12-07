package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallRightScreen(vm: GameViewModel) {
    val state = vm.gameState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // -------------------------
        // WINDOW
        // -------------------------
        Box(
            modifier = Modifier
                .size(200.dp, 130.dp)
                .background(Color.Cyan)
                .clickable { vm.onWindowClicked() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (state.flags.windowOpened) "WINDOW (OPEN)" else "WINDOW (LOCKED)"
            )
        }

        Spacer(Modifier.height(30.dp))

        // -------------------------
        // PRESENT BOX
        // -------------------------
        Box(
            modifier = Modifier
                .size(180.dp, 100.dp)
                .background(Color.Red)
                .clickable { vm.onPresentClicked() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                when {
                    state.flags.presentOpened -> "PRESENT (OPEN)"
                    !state.flags.lockerOpened -> "PRESENT (LOCKED)"
                    else -> "PRESENT"
                }
            )
        }

        Spacer(Modifier.height(30.dp))

        // -------------------------
        // TREE
        // -------------------------
        Box(
            modifier = Modifier
                .size(180.dp, 120.dp)
                .background(Color.Green),
                // .clickable { vm.onTreeClicked() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (state.flags.ornamentShelfOrdered)
                    "TREE (ACTIVE)"
                else
                    "TREE"
            )
        }
    }
}