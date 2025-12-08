package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.escaperoomapp.viewmodel.GameViewModel

@Composable
fun DotPanelZoomScreen(
    vm: GameViewModel,
    onDismiss: () -> Unit
) {
    val pattern = vm.activePattern.value
    val dotPositions = remember { mutableMapOf<Int, Offset>() }

    // === FIRST LAYER: TAP OUTSIDE TO DISMISS ===
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },   // <-- dismiss works now
        contentAlignment = Alignment.Center
    ) {

        // === SECOND LAYER: THE PANEL (does NOT dismiss) ===
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
                .background(Color(0xFF222222))
                .padding(28.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            vm.isDrawing.value = true
                        },
                        onDrag = { change, _ ->
                            detectDotHit(change.position, dotPositions) { index ->
                                vm.onDotDragOver(index)
                            }
                        },
                        onDragEnd = {
                            vm.onGestureEnd()
                        },
                        onDragCancel = {
                            vm.onGestureEnd()
                        }
                    )
                }
        ) {

            // DRAW LINES
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawPatternLines(pattern, dotPositions)
            }

            // DOT GRID
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0 until 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col + 1
                            GestureDot(
                                index = index,
                                selected = pattern.contains(index),
                                onPositionPlaced = { offset ->
                                    dotPositions[index] = offset
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun GestureDot(
    index: Int,
    selected: Boolean,
    onPositionPlaced: (Offset) -> Unit
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .onGloballyPositioned { coordinates ->
                val center = coordinates.boundsInParent().center
                onPositionPlaced(center)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (selected) Color.White else Color.DarkGray,
                    shape = CircleShape
                )
        )
    }
}

fun DrawScope.drawPatternLines(
    pattern: List<Int>,
    dotPositions: Map<Int, Offset>
) {
    if (pattern.size < 2) return

    for (i in 0 until pattern.lastIndex) {
        val a = dotPositions[pattern[i]] ?: continue
        val b = dotPositions[pattern[i + 1]] ?: continue

        drawLine(
            color = Color.White,
            start = a,
            end = b,
            strokeWidth = 8f
        )
    }
}

fun detectDotHit(
    touch: Offset,
    dotPositions: Map<Int, Offset>,
    onHit: (Int) -> Unit
) {
    val radius = 60f // Finger proximity threshold

    dotPositions.forEach { (index, pos) ->
        if ((touch - pos).getDistance() < radius) {
            onHit(index)
        }
    }
}
