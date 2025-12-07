package com.example.escaperoomapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.example.escaperoomapp.model.Direction
import com.example.escaperoomapp.model.GameState
import com.example.escaperoomapp.model.Item
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.model.Wall
import java.util.Objects.toString

class GameViewModel {
    var gameState = mutableStateOf(GameState())
        private set

    var windowZoomEvent = mutableStateOf(false)
        private set


    // CHANGED BASED ON UI
    private val correctWreathSequence = listOf(
        Direction.LEFT,
        Direction.RIGHT,
        Direction.RIGHT,
        Direction.LEFT
    )

    fun moveLeft() {
        val current = gameState.value

        when (current.currentWall) {
            Wall.WALLCENTER
                -> gameState.value = current.copy(currentWall = Wall.WALLLEFT)
            Wall.WALLRIGHT
                -> gameState.value = current.copy(currentWall = Wall.WALLCENTER)

            Wall.WALLLEFT -> null
        }
    }

    fun moveright() {
        val current = gameState.value

        when (current.currentWall) {
            Wall.WALLCENTER
                -> gameState.value = current.copy(currentWall = Wall.WALLRIGHT)
            Wall.WALLRIGHT -> null
            Wall.WALLLEFT
                -> gameState.value = current.copy(currentWall = Wall.WALLCENTER)
        }
    }

    fun addItem(item: Item) {
        val current = gameState.value
        val new = current.inventory + item

        gameState.value = current.copy(inventory = new)
    }

    fun removeItem(item: Item) {
        val current = gameState.value
        val new = current.inventory - item

        gameState.value = current.copy(inventory = new)
    }

    fun hasItem(item: Item): Boolean {
        val current = gameState.value

        if (current.inventory.contains(item))
            return true

        return false
    }

    fun interact(id: ObjectID) {
        val current = gameState.value

        when (id) {

            // Wall Center

            // DONE
            ObjectID.WC_SHELF_UNLOCKED -> {

                // If already solved, ignore interaction
                if (current.flags.openedShelf) return

                // Add item to inventory
                addItem(Item.OperaGlass)

                // Update puzzle flag
                val updatedFlags = current.flags.copy(
                    openedShelf = true
                )

                // Apply new state
                gameState.value = current.copy(
                    flags = updatedFlags
                )
            }

            ObjectID.WC_DOT_PANEL -> {

                // If already solved, ignore interaction
                if (current.flags.dotPanelSolved) return

                // Add item to inventory
                addItem(Item.Matchbox)

                // Update puzzle flag
                val updatedFlags = current.flags.copy(
                    dotPanelSolved = true
                )

                // Apply new state
                gameState.value = current.copy(
                    flags = updatedFlags
                )
            }

            // DONE
            ObjectID.WC_DOOR  -> {

                // Must have the key to unlock
                if (!hasItem(Item.Key)) return

                removeItem(Item.Key)

                // Unlock the door
                val updatedFlags = current.flags.copy(
                    doorUnlocked = true
                )

                gameState.value = current.copy(
                    flags = updatedFlags
                )
            }

            ObjectID.WC_WREATH_LEFT -> {
                val current = gameState.value

                if (!current.flags.fireplaceLit) return
                if (current.flags.wreathShaken) return

                val newInput = current.wreathInput + Direction.LEFT
                processWreathInput(newInput)
            }

            ObjectID.WC_WREATH_RIGHT -> {
                val current = gameState.value

                if (!current.flags.fireplaceLit) return
                if (current.flags.wreathShaken) return

                val newInput = current.wreathInput + Direction.RIGHT
                processWreathInput(newInput)
            }


            // Wall Left

            // DONE
            ObjectID.WL_FIREPLACE -> {
                if (current.flags.fireplaceLit) return

                // Must have the match to fire
                if (!hasItem(Item.Matchbox)) return

                removeItem(Item.Matchbox)

                // Update puzzle flag
                val updatedFlags = current.flags.copy(
                    fireplaceLit = true
                )

                // Apply new state
                gameState.value = current.copy(
                    flags = updatedFlags
                )
            }

            ObjectID.WL_ORNAMENT_SHELF -> {
                // If ornament already placed, ignore
                if (current.flags.ornamentPlaced || current.flags.ornamentShelfOrdered) return

                // Must have the ornament to place one
                if (!hasItem(Item.SnowmanOrnament)) return

                removeItem(Item.SnowmanOrnament)

                // Update puzzle flag
                val updatedFlags = current.flags.copy(
                    ornamentPlaced = true
                )

                // Apply new state
                gameState.value = current.copy(
                    flags = updatedFlags
                )
            }

            // DONE
            ObjectID.WL_SMALL_LOCKER -> {
            // If already opened, ignore
            if (current.flags.lockerOpened) return

            // If shelf is not ordered, ignore
            if (!current.flags.ornamentShelfOrdered) return

            // UI should now display the 4-digit input screen
            // No state change here
            }

            // Wall Right
            // NOT DONE: UI NEEDED
            ObjectID.WR_WINDOW  -> {
                if (!current.flags.windowOpened) return
                // If there is no opera glass, ignore
                if (!hasItem(Item.OperaGlass)) return

                // Trigger UI zoom event
                windowZoomEvent.value = true
            }

            ObjectID.WR_PRESENT_BOX  -> {

                if (current.flags.presentOpened) return
                if (!current.flags.lockerOpened) return
                if (!hasItem(Item.ChainCutter)) return

                removeItem(Item.ChainCutter)
                addItem(Item.Key)

                // Update puzzle flag
                val updatedFlags = current.flags.copy(
                    presentOpened = true
                )

                // Apply new state
                gameState.value = current.copy(
                    flags = updatedFlags
                )
            }

            ObjectID.WR_TREE   -> {
                // If the ornaments are not lined correctly, ignore
                if (!current.flags.ornamentShelfOrdered) return
            }
        }
    }

    fun processWreathInput(newInput: List<Direction>) {
        val current = gameState.value

        // If sequence matches exactly → puzzle solved
        if (newInput == correctWreathSequence) {

            addItem(Item.SnowmanOrnament)

            val updatedFlags = current.flags.copy(
                wreathShaken = true,
                windowOpened = true
            )

            gameState.value = current.copy(
                wreathInput = emptyList(),
                flags = updatedFlags
            )

            return
        }

        // If user exceeded the length but didn't match → reset
        if (newInput.size >= correctWreathSequence.size) {
            gameState.value = current.copy(wreathInput = emptyList())
            return
        }

        // Otherwise continue building the sequence
        gameState.value = current.copy(
            wreathInput = newInput
        )
    }

    fun markShelfOrdered() {
        val current = gameState.value

        // Already solved
        if (current.flags.ornamentShelfOrdered) return

        val updatedFlags = current.flags.copy(
            ornamentShelfOrdered = true
        )

        gameState.value = current.copy(flags = updatedFlags)
    }

    fun solveLocker() {
        val current = gameState.value

        // If already opened, ignore
        if (current.flags.lockerOpened) return

        // Reward item: ChainCutter
        addItem(Item.ChainCutter)

        // Update puzzle flag
        val updatedFlags = current.flags.copy(
            lockerOpened = true
        )

        // Apply new state
        gameState.value = current.copy(
            flags = updatedFlags
        )
    }


}