package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.escaperoomapp.ui.navigation.StartScreenRoute
import com.example.escaperoomapp.R
import com.example.escaperoomapp.viewmodel.GameViewModel


@Composable
fun EndScreen(
    backStack: NavBackStack<NavKey>
) {
    val vm: GameViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.ending_screen),
            contentDescription = "End Screen Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark overlay for cinematic effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.40f))
        )

        // MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Merry Christmas!",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            /*
            // Restart button
            Box(
                modifier = Modifier
                    .clickable {
                        vm.resetGame()
                        backStack.add(StartScreenRoute)
                    }
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

             */
        }
    }
}

