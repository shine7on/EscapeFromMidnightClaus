package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.escaperoomapp.ui.screen.zoom.*
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
import com.example.escaperoomapp.model.Item
import com.example.escaperoomapp.model.Wall
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun GameScreen(
    backStack: NavBackStack<NavKey>,
) {
    val vm = remember { GameViewModel() }
    val state = vm.gameState.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (state.currentWall) {
            Wall.WALLLEFT -> WallLeftScreen(vm)
            Wall.WALLCENTER -> WallCenterScreen(vm)
            Wall.WALLRIGHT -> WallRightScreen(vm)
        }

        if (vm.isWindowZoomOpen.value) WindowZoomScreen { vm.closeWindowZoom() }
        if (vm.isPresentZoomOpen.value) PresentZoomScreen { vm.closePresentZoom() }
        if (vm.isDoorZoomOpen.value) DoorZoomScreen(vm) { vm.closeDoorZoom() }
        if (vm.isPaintingZoomOpen.value) { PaintingZoomDialog { vm.closePaintingZoom() } }
        if (vm.isShelfZoomOpen.value) {
            ShelfZoomScreen(
                vm = vm,
                hasOrnament = vm.hasItem(Item.SnowmanOrnament),
                ornamentPlaced = vm.gameState.value.flags.ornamentPlaced,
                onDismiss = { vm.closeShelfZoom() }
            )
        }

        if (vm.isWreathPuzzleOpen.value) {
            WreathZoomScreen(
                vm = vm,
                onDismiss = { vm.closeWreathPuzzle() }
            )
        }

        if (vm.isLockerZoomOpen.value) {
            LockerZoomScreen(vm) { vm.closeLockerZoom() }
        }

        if (vm.isFireplaceZoomOpen.value) FireplaceZoomScreen(vm) { vm.closeFireplaceZoom() }

        // NEW
        if (vm.isCabinetZoomOpen.value) {
            CabinetZoomScreen(vm) { vm.closeCabinetZoom() }
        }
        if (vm.isDotPanelZoomOpen.value) {
            DotPanelZoomScreen(vm) { vm.closeDotPanelZoom() }
        }
        /*
        if (vm.isBloodDialogOpen.value) {
            BloodDialog { vm.closeBloodDialog() }
        }

         */


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
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
            .padding(bottom = 80.dp),   // ← raises it higher!
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
            Spacer(modifier = Modifier.width(50.dp))
        }

        // RIGHT ARROW (only if allowed)
        if (wall != Wall.WALLRIGHT) {
            ArrowButton(text = "→") { vm.moveRight() }
        } else {
            Spacer(modifier = Modifier.width(50.dp))
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

