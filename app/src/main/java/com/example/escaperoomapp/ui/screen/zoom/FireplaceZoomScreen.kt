package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.escaperoomapp.R
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun FireplaceZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val state = vm.gameState.value
    val frame = vm.fireplaceFrame.value   // ← FIREPLACE ANIMATION FRAME

    // Pick fireplace sprite based on animation frame
    val imageRes = when {
        !state.flags.fireplaceLit -> R.drawable.fireplace_off
        else -> when (frame) {
            1 -> R.drawable.fireplace_left
            2 -> R.drawable.fireplace_right
            else -> R.drawable.fireplace_center
        }
    }

    // Start animation when entering the screen, stop when leaving
    DisposableEffect(Unit) {
        vm.startFireAnimation()

        onDispose {
            vm.stopFireAnimation()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable {
                vm.stopFireAnimation()
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E)
            )
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Fireplace Zoom",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}
