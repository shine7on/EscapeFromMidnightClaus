package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel
import com.example.escaperoomapp.R

@Composable
fun WallCenterScreen(vm: GameViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Full background
        Image(
            painter = painterResource(id = R.drawable.center_wall),
            contentDescription = "Center Wall",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Click zones overlay
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // WREATH (tap to open wreath zoom)
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 15.dp, y = 200.dp)
                    .size(width = 130.dp, height = 130.dp)
                    .clickable { vm.onWreathClicked() }
            )

            // DOOR (tap to see lock zoom)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-40).dp, y = 100.dp)
                    .size(width = 30.dp, height = 50.dp)
                    .clickable { vm.onLockedDoorClicked() }
            )

            // CABINET (books + drawers at bottom-right)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 30.dp, y = (-180).dp)
                    .size(width = 100.dp, height = 100.dp)
                    .clickable { vm.openCabinetZoom() }
            )

            // BLOOD STAIN (dialog only)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 140.dp, y = 50.dp)
                    .size(width = 60.dp, height = 90.dp)
                    .clickable { vm.openBloodDialog() }
            )
        }
    }
}