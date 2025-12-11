package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.Item
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun PresentZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val state = vm.gameState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E)
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.present_zoom),
                contentDescription = "Zoomed Present Box",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = (vm.selectedItem.value == Item.Knife)
                    ) {
                        vm.interact(ObjectID.WR_PRESENT_BOX)
                        onDismiss()
                    },
                contentScale = ContentScale.Fit
            )
        }

    }
}
