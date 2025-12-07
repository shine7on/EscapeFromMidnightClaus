package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.escaperoomapp.model.Wall
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun GameScreen(
    backStack: NavBackStack<NavKey>,
) {
    val vm = remember { GameViewModel() }
    val state = vm.gameState.value

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        when (state.currentWall) {
            Wall.WALLLEFT -> WallLeftScreen(vm)
            Wall.WALLCENTER -> WallCenterScreen(vm)
            Wall.WALLRIGHT -> WallRightScreen(vm)
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
        ) {
            BottomNav(vm)
        }

        InventoryBarPlaceholder(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}



@Composable
fun InventoryBarPlaceholder(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text("INVENTORY (coming later)")
    }
}

@Composable
fun BottomNav(vm: GameViewModel) {
    val wall = vm.gameState.value.currentWall

    Row(Modifier.fillMaxWidth()) {

        // LEFT BUTTON (only if allowed)
        if (wall != Wall.WALLLEFT) {
            Button(onClick = { vm.moveLeft() }) {
                Text("←")
            }
        } else {
            Spacer(modifier = Modifier.width(80.dp)) // keeps layout consistent
        }

        Spacer(modifier = Modifier.weight(1f))

        // RIGHT BUTTON (only if allowed)
        if (wall != Wall.WALLRIGHT) {
            Button(onClick = { vm.moveright() }) {
                Text("→")
            }
        } else {
            Spacer(modifier = Modifier.width(80.dp))
        }
    }
}
