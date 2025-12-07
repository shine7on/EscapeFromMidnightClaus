package com.example.escaperoomapp.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.model.ObjectID

@Composable
fun WallRightScreen(vm: GameViewModel) {

    val zoomRequested = vm.windowZoomEvent.value

    // Main wall UI
    Box(modifier = Modifier.fillMaxSize()) {

        // --------------------------
        // Placeholder: Window area
        // --------------------------
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter)
                .background(Color.Cyan)
                .clickable {
                    vm.interact(ObjectID.WR_WINDOW)
                }
        ) {
            Text(
                "WINDOW",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // --------------------------
        // Placeholder: Present Box
        // --------------------------
        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.Center)
                .background(Color.Red)
                .clickable {
                    vm.interact(ObjectID.WR_PRESENT_BOX)
                }
        ) {
            Text(
                "PRESENT",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // --------------------------
        // Placeholder: Tree
        // --------------------------
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Green)
                .clickable {
                    vm.interact(ObjectID.WR_TREE)
                }
        ) {
            Text(
                "TREE",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // --------------------------
        // Zoom Overlay
        // --------------------------
        if (zoomRequested) {
            WindowZoomView(
                onDismiss = { vm.windowZoomEvent.value = false }
            )
        }
    }
}

@Composable
fun WindowZoomView(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
    ) {

        // Placeholder for zoomed window image
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
                .background(Color.White)
        ) {
            Text(
                "ZOOMED WINDOW HINT\n(star → snowman → snow → house)",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }

        Button(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Text("Close")
        }
    }
}
