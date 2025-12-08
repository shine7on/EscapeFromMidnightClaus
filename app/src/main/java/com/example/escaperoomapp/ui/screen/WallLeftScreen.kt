package com.example.escaperoomapp.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.Direction

@Composable
fun WallLeftScreen(vm: GameViewModel) {

    val state = vm.gameState.value

    val imageRes = when {
        !state.flags.fireplaceLit -> R.drawable.left_wall_nosnowmannofire
        else -> R.drawable.left_wall_nosnowmannofire
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        // -----------------------
        // FULL WALL IMAGE
        // -----------------------
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Left Wall",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // -----------------------
        // PAINTING GRID ZONE (big area)
        // Approx: Left half of the wall
        // -----------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(100.dp))

            // -----------------------
            // PAINTING GRID AREA
            // Top half
            // -----------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.45f)
                    .clickable {
                        // Later: painting puzzle
                        vm.openPaintingZoom()
                    }
            )

            Spacer(Modifier.height(30.dp))

            // -----------------------
            // SHELF AREA (full width)
            // -----------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable { vm.isShelfZoomOpen.value = true } // ← OPEN ZOOM ONLY
            )

            Spacer(Modifier.height(16.dp))

            // -----------------------
            // FIREPLACE AREA (centered, half width)
            // -----------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { vm.interact(ObjectID.WL_FIREPLACE) }
            ) {
                FireplaceImage(vm)
            }
        }
    }
}

@Composable
fun FireplaceImage(vm: GameViewModel) {
    val state = vm.gameState.value
    val last = state.wreathInput.lastOrNull()

    val imageRes = when {
        !state.flags.fireplaceLit -> R.drawable.fireplace_off

        last == com.example.escaperoomapp.model.Direction.LEFT -> R.drawable.fireplace_left
        last == Direction.RIGHT -> R.drawable.fireplace_right

        else -> R.drawable.fireplace_center
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}

