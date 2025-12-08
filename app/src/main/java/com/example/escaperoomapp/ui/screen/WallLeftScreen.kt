package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

        // 1. NO FIRE + NO SNOWMAN
        !state.flags.fireplaceLit && !state.flags.ornamentPlaced ->
            R.drawable.left_wall_nosnowman_nofire

        // 2. FIRE + NO SNOWMAN
        state.flags.fireplaceLit && !state.flags.ornamentPlaced ->
            R.drawable.left_wall_nosnowman_fire

        else ->
            R.drawable.left_wall_snowman_fire
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
                    .height(180.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { vm.interact(ObjectID.WL_FIREPLACE) }
            )
        }
    }
}

