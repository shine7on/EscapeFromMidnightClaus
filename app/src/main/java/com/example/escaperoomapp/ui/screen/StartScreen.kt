package com.example.escaperoomapp.ui.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.escaperoomapp.R
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.escaperoomapp.ui.navigation.GameScreenRoute


@Composable
fun StartScreen(
    backStack: NavBackStack<NavKey>
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.start_screen),
            contentDescription = "Start Screen Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 260.dp),   // 👈 Adjust this to place text exactly below "MIDNIGHT CLAUS"
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Press here to start",
                color = Color(0xFF800020), // Burgundy
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif,   // 👈 Gothic-ish
                modifier = Modifier
                    .clickable {
                        backStack.add(GameScreenRoute)
                    }
            )
        }
    }
}


