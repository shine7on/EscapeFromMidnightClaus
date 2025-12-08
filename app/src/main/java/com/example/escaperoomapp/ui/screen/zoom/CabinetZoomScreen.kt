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
                painter = painterResource(id = R.drawable.wreath_zoom), //center_cabinet_zoom
                contentDescription = "Cabinet",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // TOP drawer → opera glass (reusing WC_SHELF_UNLOCKED logic)
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 80.dp)
                    .size(width = 220.dp, height = 90.dp)
                    .clickable {
                        vm.interact(ObjectID.WC_SHELF_UNLOCKED)
                    }
            )

            // BOTTOM drawer → 9-dot panel zoom
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-40).dp)
                    .size(width = 220.dp, height = 90.dp)
                    .clickable {
                        vm.openDotPanelZoom()
                    }
            )
        }

        Text(
            text = "Top: something is inside.\nBottom: strange 9-dot device.",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
