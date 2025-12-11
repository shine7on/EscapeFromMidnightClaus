package com.example.escaperoomapp.ui.screen.zoom

import android.R.attr.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewModelScope
import com.example.escaperoomapp.R
import com.example.escaperoomapp.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

@Composable
fun LockerZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val input = vm.lockerInput.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(1f)
        ) {

            Image(
                painter = painterResource(id = R.drawable.keypad_image),
                contentDescription = "Locker Keypad",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // ===========================
            // DIGIT BUTTON HOTSPOTS
            // ===========================
            // These will be refined once we crop the exact sizes.
            // For now the grid works & is interactable.

            val buttonSize = 26.dp
            val startX = 160.dp   // adjust after testing
            val startY = 158.dp   // adjust after testing
            val gapX = 31.dp
            val gapY = 31.dp

            val labels = listOf(
                "1","2","3",
                "4","5","6",
                "7","8","9",
                "*","0","#"
            )

            var index = 0
            for (row in 0 until 4) {
                for (col in 0 until 3) {
                    val label = labels[index]

                    Box(
                        modifier = Modifier
                            .offset(x = startX + col * gapX, y = startY + row * gapY)
                            .size(buttonSize)
                            .clickable {
                                // if (label in listOf("*","#")) return@clickable
                                if (vm.lockerInput.value.length < 4) {
                                    vm.lockerInput.value += label

                                    if (vm.lockerInput.value.length == 4) {
                                        // Let UI show 4th digit before resetting
                                        vm.viewModelScope.launch {
                                            delay(300)   // 300ms pause so user sees input
                                            vm.submitLockerCode()
                                        }
                                    }
                                }
                            }
                    )
                    index++
                }
            }
        }

        // === Input TEXT Display ===
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = input,
                color = Color.White,
                fontSize = 28.sp
            )
            Text(
                text = "(tap outside keypad to close)",
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}