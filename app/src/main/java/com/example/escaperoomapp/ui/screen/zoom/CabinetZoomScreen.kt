package com.example.escaperoomapp.ui.screen.zoom

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
import androidx.compose.ui.unit.sp
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun CabinetZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val flags = vm.gameState.value.flags

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.cabinet_zoom),
                contentDescription = "Cabinet",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // TOP drawer → opera glass (reusing WC_SHELF_UNLOCKED logic)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-10).dp, y = 1.dp)
                    .size(width = 130.dp, height = 40.dp)
                    .clickable(enabled = !flags.openedShelf) {
                        vm.interact(ObjectID.WC_CABINET_UNLOCKED)
                    }
            )

            // BOTTOM drawer → 9-dot panel zoom
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-10).dp, y = 60.dp)
                    .size(width = 130.dp, height = 40.dp)
                    .clickable (enabled = !vm.gameState.value.flags.dotPanelSolved) {
                        vm.openDotPanelZoom()
                    }
            )
        }
    }
}
