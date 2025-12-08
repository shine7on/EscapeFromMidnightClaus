package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun DotPanelZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.4f)
                .clickable {
                    // For now: solve immediately & give Matchbox
                    vm.interact(ObjectID.WC_DOT_PANEL)
                    onDismiss()
                },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF222222)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "9-dot puzzle (placeholder)\nTap to solve.",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}
