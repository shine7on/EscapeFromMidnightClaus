package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.R
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun FireplaceZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val state = vm.gameState.value

    // Select correct image
    val imageRes = when {
        !state.flags.fireplaceLit -> R.drawable.fireplace_off
        else -> R.drawable.fireplace_center //  state.flags.wreathInput.isEmpty()
        // state.wreathInput.lastOrNull()?.name == "LEFT" -> R.drawable.fireplace_center
        // else -> R.drawable.fireplace_center
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
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
