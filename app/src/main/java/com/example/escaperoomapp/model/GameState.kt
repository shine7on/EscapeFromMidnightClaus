package com.example.escaperoomapp.model

/*
    GameState represents the entire current world of your escape room.
 */
data class GameState(
    val currentWall: Wall = Wall.WALLCENTER,
    val inventory: List<Item> = emptyList<Item>(),
    val flags: PuzzleFlags = PuzzleFlags(),
    val wreathInput: List<Direction> = emptyList()
)