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
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallCenterScreen(vm: GameViewModel) {
    val state = vm.gameState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(Modifier.height(24.dp))

        // ===== WREATH =====
        Box(
            modifier = Modifier
                .size(130.dp)
                .background(Color(0xFF2E8B57))
                .clickable { vm.onWreathClicked() },
            contentAlignment = Alignment.Center
        ) { Text("WREATH") }

        Spacer(Modifier.height(24.dp))

        // ===== DOOR =====
        Box(
            modifier = Modifier
                .size(180.dp, 240.dp)
                .background(
                    if (state.flags.doorUnlocked) Color.Green else Color.DarkGray
                )
                .clickable { vm.onLockedDoorClicked() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (state.flags.doorUnlocked) "DOOR (Unlocked)" else "DOOR (Locked)",
                color = Color.White
            )
        }

        Spacer(Modifier.height(24.dp))

        // ===== SHELF =====
        Box(
            modifier = Modifier
                .size(160.dp, 80.dp)
                .background(Color(0xFFB5651D))
                .clickable { vm.interact(ObjectID.WC_SHELF_UNLOCKED) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (state.flags.openedShelf) "SHELF (empty)" else "SHELF (closed)"
            )
        }
    }
}
