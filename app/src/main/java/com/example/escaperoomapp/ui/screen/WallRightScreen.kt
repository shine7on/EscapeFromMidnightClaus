package com.example.escaperoomapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun WallRightScreen(vm: GameViewModel) {

    val state = vm.gameState.value

    // window image changes depending on curtain state
    val rightWallImage = if (state.flags.windowOpened)
        R.drawable.right_wall
    else
        R.drawable.right_wall

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = rightWallImage),
            contentDescription = "Right Wall",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Layout roughly aligned with your image
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // --------------- WINDOW ZONE ---------------
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset((-80).dp, 50.dp)
                    .size(180.dp, 150.dp)
                    .clickable(state.flags.windowOpened) {
                        vm.onWindowClicked()
                    }
                    .background(Color.Red)
            )

            Spacer(Modifier.height(20.dp))

            // --------------- TREE AREA ---------------
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-40).dp, (-150).dp)
                    .size(120.dp, 240.dp)
                    .clickable { vm.openTreeZoom() }
                    .background(Color.Red)
            )

            Spacer(Modifier.height(20.dp))

            // --------------- PRESENT BOX ---------------
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset((-60).dp, (-150).dp)
                    .size(160.dp, 100.dp)
                    .clickable {
                        vm.interact(ObjectID.WR_PRESENT_BOX)
                    }
                    .background(Color.Red)
            )
        }
    }
}
