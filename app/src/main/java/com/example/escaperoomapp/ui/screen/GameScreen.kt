package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        InventoryBar(
            vm = vm,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}



@Composable
fun InventoryBar(
    vm: GameViewModel,
    modifier: Modifier = Modifier
) {
    val state = vm.gameState.value
    val items = state.inventory

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 90.dp),   // ← raises it higher!
        contentAlignment = Alignment.Center
    ) {

        // Floating background
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(80.dp)
                .background(
                    color = Color(0xDD1A1A1A), // translucent dark
                    // shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val maxSlots = 4

                // Render each slot
                for (i in 0 until maxSlots) {
                    if (i < items.size) {
                        InventorySlot(
                            imageName = "X", // plug image later
                            filled = true
                        )
                    } else {
                        InventorySlot(
                            imageName = "",
                            filled = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InventorySlot(imageName: String, filled: Boolean) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = if (filled) Color(0xFF444444) else Color(0x55222222),
                // shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (filled) {
            // Real image goes here later
            Text(
                text = imageName.take(3).uppercase(),
                color = Color.White
            )
        }
    }
}



@Composable
fun BottomNav(vm: GameViewModel) {

    val wall = vm.gameState.value.currentWall

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 100.dp), // ← positions arrows slightly above inventory bar
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // LEFT ARROW (only if allowed)
        if (wall != Wall.WALLLEFT) {
            ArrowButton(text = "←") { vm.moveLeft() }
        } else {
            Spacer(modifier = Modifier.width(80.dp))
        }

        // RIGHT ARROW (only if allowed)
        if (wall != Wall.WALLRIGHT) {
            ArrowButton(text = "→") { vm.moveRight() }
        } else {
            Spacer(modifier = Modifier.width(80.dp))
        }
    }
}

@Composable
fun ArrowButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 70.dp, height = 50.dp)
            .background(
                color = Color(0xFF2D2D2D),        // same tone as inventory bar
                // shape = RoundedCornerShape(12.dp) // rounded rectangle
            )
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = Color.White,
            fontSize = 26.sp
        )
    }
}

