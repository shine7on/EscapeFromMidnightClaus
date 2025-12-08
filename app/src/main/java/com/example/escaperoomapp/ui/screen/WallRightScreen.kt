package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallRightScreen(vm: GameViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        // -----------------------
        // FULL WALL IMAGE
        // -----------------------
        Image(
            painter = painterResource(id = R.drawable.right_wall),
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
            )
        }
    }
}
