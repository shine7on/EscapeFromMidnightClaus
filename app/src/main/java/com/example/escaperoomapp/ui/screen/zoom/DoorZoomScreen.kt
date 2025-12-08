package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.R
import com.example.escaperoomapp.model.Item
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun DoorZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val state = vm.gameState.value
    val flags = state.flags
    val hasKey = vm.hasItem(Item.Key)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        // Which image should show?
        val imageRes = if (flags.doorUnlocked)
            R.drawable.door_zoom  // FIX: open the door
        else
            R.drawable.door_zoom

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            // Zoomed doorknob image
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // Doorknob clickable area
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(160.dp)
                    .offset(x = (-40).dp)  // adjust based on image
                    .clickable {

                        when {
                            flags.doorUnlocked -> {
                                // Already open - maybe play door opening sound later
                            }

                            hasKey -> {
                                vm.interact(ObjectID.WC_DOOR) // unlock
                            }
                        }
                    }
            )
        }
    }
}
