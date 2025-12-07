package com.example.escaperoomapp.ui.screen.zoom

import androidx.compose.runtime.Composable

@Composable
fun DoorZoomScreen(onClose: () -> Unit) {
    ZoomPlaceholder("LOCKED DOOR", onClose)
}