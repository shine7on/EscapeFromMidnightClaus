package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WreathZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val anim = vm.wreathAnimState.value

    LaunchedEffect(anim) {
        if (anim == "left" || anim == "right") {
            kotlinx.coroutines.delay(250)   // 0.25 seconds
            vm.wreathAnimState.value = "center"
        }
    }

    val imageRes = when (anim) {
        "left" -> R.drawable.wreath_left
        "right" -> R.drawable.wreath_right
        else -> R.drawable.wreath_zoom
    }

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
                .fillMaxHeight(0.7f)
        ) {
            // base wreath image (you can swap with left/right tilted variants later)
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Wreath",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // Left half → LEFT input
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(0.45f)
                    .align(Alignment.CenterStart)
                    .clickable {
                        vm.interact(ObjectID.WC_WREATH_LEFT)
                    }
            )

            // Right half → RIGHT input
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(0.45f)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        vm.interact(ObjectID.WC_WREATH_RIGHT)
                    }

            )
        }
    }
}

@Composable
fun WreathFailDialog(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.height(300.dp))

            Text(
                text = "Nothing happened…",
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
