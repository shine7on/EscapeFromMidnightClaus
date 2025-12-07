package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallLeftScreen(vm: GameViewModel) {
    val state = vm.gameState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ===== 3×3 PAINTING GRID (Dot puzzle) =====
        Column {
            repeat(3) { row ->
                Row {
                    repeat(3) { col ->
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .background(Color.Gray)
                                .padding(4.dp)
                                .clickable { vm.interact(ObjectID.WC_DOT_PANEL) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("•")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ===== LOCKER (4-digit) =====
        Box(
            modifier = Modifier
                .size(160.dp, 90.dp)
                .background(Color.DarkGray)
                .clickable { vm.interact(ObjectID.WL_SMALL_LOCKER) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (state.flags.lockerOpened) "LOCKER (opened)" else "LOCKER (locked)"
            )
        }

        Spacer(Modifier.height(24.dp))

        // ===== ORNAMENT SHELF =====
        Box(
            modifier = Modifier
                .size(200.dp, 60.dp)
                .background(Color(0xFF8B4513))
                .clickable { vm.interact(ObjectID.WL_ORNAMENT_SHELF) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                when {
                    state.flags.ornamentShelfOrdered -> "ORNAMENT SHELF (ordered)"
                    state.flags.ornamentPlaced -> "ORNAMENT (placed)"
                    else -> "ORNAMENT SHELF"
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        // ===== FIREPLACE =====
        Box(
            modifier = Modifier
                .size(200.dp, 120.dp)
                .background(if (state.flags.fireplaceLit) Color.Red else Color.DarkGray)
                .clickable { vm.interact(ObjectID.WL_FIREPLACE) },
            contentAlignment = Alignment.Center
        ) {
            Text(if (state.flags.fireplaceLit) "FIRE (lit)" else "FIREPLACE")
        }
    }
}

