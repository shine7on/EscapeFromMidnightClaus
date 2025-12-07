package com.example.escaperoomapp.model

data class PuzzleFlags(
    val openedShelf: Boolean = false,
    val dotPanelSolved: Boolean = false,
    val fireplaceLit: Boolean = false,
    val wreathShaken: Boolean = false,
    val windowOpened: Boolean = false,
    val ornamentPlaced: Boolean = false,
    val ornamentShelfOrdered: Boolean = false,
    val lockerOpened: Boolean = false,
    val presentOpened: Boolean = false,
    val doorUnlocked: Boolean = false
)