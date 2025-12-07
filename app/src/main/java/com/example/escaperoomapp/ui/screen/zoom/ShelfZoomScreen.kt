package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.escaperoomapp.R


@Composable
fun ShelfZoomScreen(
    hasOrnament: Boolean,
    ornamentPlaced: Boolean,
    onPlace: () -> Unit,
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
            if (ornamentPlaced) R.drawable.shelf_zoom_nosnowman
            else R.drawable.shelf_zoom_nosnowman

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // Only allow clicking to place ornament when:
            // 1. User has item
            // 2. Shelf is not already solved
            if (hasOrnament && !ornamentPlaced) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(120.dp)
                        .clickable {
                            onPlace()
                            onDismiss()
                        }
                )
            }
        }
    }
}
