package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun ShelfZoomScreen(
    vm: GameViewModel,
    ornamentPlaced: Boolean,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        val imageRes =
            if (ornamentPlaced) R.drawable.shelf_zoom_withsnowman
            else R.drawable.shelf_zoom_nosnowman

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )


            // ORNAMENT placement area
            if (!ornamentPlaced) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 60.dp)
                        .size(60.dp)
                        .clickable {
                            vm.interact(ObjectID.WL_ORNAMENT_SHELF)
                            onDismiss()
                        }
                )
            }

            // LOCKER AREA (click to zoom to locker)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
                    .offset(x = 10.dp, y = 20.dp)
                    .clickable { vm.openLockerZoom() }
            )
        }
    }
}
