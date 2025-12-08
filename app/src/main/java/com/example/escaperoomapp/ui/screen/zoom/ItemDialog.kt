package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.escaperoomapp.model.Item

@Composable
fun FoundItemDialog(
    item: Item,
    iconRes: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(0xFF222222))
                .padding(24.dp)
        ) {

            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "You found:\n${item.displayName()}",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "(Tap anywhere to close)",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

fun Item.displayName(): String = when(this) {
    Item.SnowmanOrnament -> "Snowman Ornament"
    Item.Matchbox -> "Matchbox"
    Item.OperaGlass -> "Opera Glass"
    Item.ChainCutter -> "Chain Cutter"
    Item.Key -> "Key"
}


@Composable
fun InspectItemDialog(
    item: Item,
    iconRes: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(0xFF222222))
                .padding(24.dp)
        ) {

            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(110.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = item.displayName(),
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}


