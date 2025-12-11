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

    val dotPositions = remember { mutableMapOf<Int, Offset>() } // will fill after layout

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
                .background(Color(0xFF222222))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { vm.isDrawing.value = true },

                        onDrag = { change, _ ->
                            detectDotHitCanvas(
                                change.position,
                                dotPositions
                            ) { index ->
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
            val w = size.width
            val h = size.height

            // place 9 dots evenly
            val cellW = w / 4
            val cellH = h / 4

            val radius = minOf(cellW, cellH) * 0.20f

            // compute dot positions
            var index = 1
            for (row in 1..3) {
                for (col in 1..3) {
                    val cx = col * cellW
                    val cy = row * cellH

                    val pos = Offset(cx, cy)
                    dotPositions[index] = pos

                    // Draw dot
                    drawCircle(
                        color = if (pattern.contains(index)) Color.White else Color.DarkGray,
                        radius = radius,
                        center = pos
                    )

                    index++
                }
            }

            // Draw connecting lines
            if (pattern.size > 1) {
                for (i in 0 until pattern.lastIndex) {
                    val a = dotPositions[pattern[i]]!!
                    val b = dotPositions[pattern[i + 1]]!!

                    drawLine(
                        color = Color.White,
                        start = a,
                        end = b,
                        strokeWidth = 10f
                    )
                }
            }
        }
    }
}


fun detectDotHitCanvas(
    touch: Offset,
    dotPositions: Map<Int, Offset>,
    onHit: (Int) -> Unit
) {
    val radius = 80f

    dotPositions.forEach { (index, pos) ->
        if ((touch - pos).getDistance() < radius) {
            onHit(index)
        }
    }
}
