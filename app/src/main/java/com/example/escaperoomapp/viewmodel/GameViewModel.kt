package com.example.escaperoomapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.escaperoomapp.model.Direction
import com.example.escaperoomapp.model.GameState
import com.example.escaperoomapp.model.Item
import com.example.escaperoomapp.model.ObjectID
import com.example.escaperoomapp.model.Wall

class GameViewModel  : ViewModel() {
    var gameState = mutableStateOf(GameState())
        private set

    // -----------------------
    // ZOOM & PUZZLE DIALOG STATES
    // -----------------------
    var isWindowZoomOpen = mutableStateOf(false)
    var isWreathPuzzleOpen = mutableStateOf(false)
    var isPresentZoomOpen = mutableStateOf(false)
    var isShelfZoomOpen = mutableStateOf(false)
    var isDoorZoomOpen = mutableStateOf(false)


    // CHANGED BASED ON UI
    private val correctWreathSequence = listOf(
        Direction.RIGHT,
        Direction.LEFT,
        Direction.LEFT,
        Direction.RIGHT,
        Direction.LEFT
    )

    // -----------------------
    // MOVEMENT
    // -----------------------
    fun moveLeft() {
        val wall = gameState.value.currentWall
        when (wall) {
            Wall.WALLCENTER -> setWall(Wall.WALLLEFT)
            Wall.WALLRIGHT -> setWall(Wall.WALLCENTER)
            else -> {}
        }
    }

    fun moveRight() {
        val wall = gameState.value.currentWall
        when (wall) {
            Wall.WALLCENTER -> setWall(Wall.WALLRIGHT)
            Wall.WALLLEFT -> setWall(Wall.WALLCENTER)
            else -> {}
        }
    }

    private fun setWall(newWall: Wall) {
        gameState.value = gameState.value.copy(currentWall = newWall)
    }

    // -----------------------
    // CLICK EVENT FUNCTIONS
    // -----------------------
    fun onWindowClicked() {
        isWindowZoomOpen.value = true
    }

    fun onWreathClicked() {
        isWreathPuzzleOpen.value = true
    }

    fun onPresentClicked() {
        isPresentZoomOpen.value = true
    }

    fun onShelfClicked() {
        isShelfZoomOpen.value = true
    }

    fun onLockedDoorClicked() {
        isDoorZoomOpen.value = true
    }

    // -----------------------
    // CLOSE / DISMISS ZOOMS
    // -----------------------
    fun closeWindowZoom() { isWindowZoomOpen.value = false }
    fun closePresentZoom() { isPresentZoomOpen.value = false }
    fun closeShelfZoom() { isShelfZoomOpen.value = false }
    fun closeDoorZoom() { isDoorZoomOpen.value = false }

    var isPaintingZoomOpen = mutableStateOf(false)

    fun openPaintingZoom() {
        isPaintingZoomOpen.value = true
    }

    fun closePaintingZoom() {
        isPaintingZoomOpen.value = false
    }

    var isFireplaceZoomOpen = mutableStateOf(false)

    fun openFireplaceZoom() {
        isFireplaceZoomOpen.value = true
    }

    fun closeFireplaceZoom() {
        isFireplaceZoomOpen.value = false
    }

    var isLockerZoomOpen = mutableStateOf(false)

    fun openLockerZoom() { isLockerZoomOpen.value = true }
    fun closeLockerZoom() { isLockerZoomOpen.value = false }

    var lockerInput = mutableStateOf("")
    private val lockerCode = "5324"

    fun submitLockerCode() {
        if (lockerInput.value == lockerCode) {
            solveLocker()
            closeLockerZoom()
            // maybe show dialog later
        } else {
            lockerInput.value = ""
        }
    }

    // Center wall zooms
    var isCabinetZoomOpen = mutableStateOf(false)
    var isDotPanelZoomOpen = mutableStateOf(false)
    var isBloodDialogOpen = mutableStateOf(false)

    // Open / close helpers
    fun openCabinetZoom() { isCabinetZoomOpen.value = true }
    fun closeCabinetZoom() { isCabinetZoomOpen.value = false }

    fun openDotPanelZoom() { isDotPanelZoomOpen.value = true }
    fun closeDotPanelZoom() { isDotPanelZoomOpen.value = false }

    fun openBloodDialog() { isBloodDialogOpen.value = true }
    fun closeBloodDialog() { isBloodDialogOpen.value = false }

    var isWreathFailDialogOpen = mutableStateOf(false)
    fun closeWreathFailDialog() { isWreathFailDialogOpen.value = false }




    fun addItem(item: Item) {
        val current = gameState.value
        val new = current.inventory + item

        gameState.value = current.copy(inventory = new,
            wreathInput = emptyList())
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

                // if (!current.flags.fireplaceLit) return
                // if (current.flags.wreathShaken) return

                wreathAnimState.value = "left"

                val newInput = gameState.value.wreathInput + Direction.LEFT
                processWreathInput(newInput)
            }

            ObjectID.WC_WREATH_RIGHT -> {
                val current = gameState.value

                // if (!current.flags.fireplaceLit) return
                // if (current.flags.wreathShaken) return

                wreathAnimState.value = "right"

                val newInput = current.wreathInput + Direction.RIGHT
                processWreathInput(newInput)
            }


            // Wall Left

            // DONE
            ObjectID.WL_FIREPLACE -> {
                // Always open zoom first
                openFireplaceZoom()

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
                isWindowZoomOpen.value = true
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

    // Wreath animation state: "center", "left", "right"
    var wreathAnimState = mutableStateOf("center")

    var foundItemDialogOpen = mutableStateOf(false)
    var lastFoundItem = mutableStateOf<Item?>(null)

    var selectedItem = mutableStateOf<Item?>(null)
    var isItemInspectDialogOpen = mutableStateOf(false)

    fun inspectItem(item: Item) {
        selectedItem.value = item
        isItemInspectDialogOpen.value = true
    }

    fun processWreathInput(newInput: List<Direction>) {
        val current = gameState.value

        // FULL MATCH → solve puzzle
        if (newInput == correctWreathSequence) {

            println("🎄 Adding Snowman Ornament!")

            val new = current.inventory + Item.Matchbox

            val updatedFlags = current.flags.copy(
                wreathShaken = true,
                windowOpened = true
            )

            gameState.value = current.copy(inventory = new,
                wreathInput = emptyList(),
                flags = updatedFlags
            )

            // trigger dialog
            lastFoundItem.value = Item.Matchbox
            foundItemDialogOpen.value = true

            wreathAnimState.value = "center"
            return
        }

        // INPUT TOO LONG → reset attempt
        if (newInput.size >= correctWreathSequence.size) {
            // WRONG SEQUENCE → RESET + SHOW DIALOG
            isWreathFailDialogOpen.value = true

            gameState.value = current.copy(wreathInput = emptyList())

            // Reset animation too
            wreathAnimState.value = "center"
            return
        }

        // Otherwise continue building
        gameState.value = current.copy(
            wreathInput = newInput
        )
    }

    fun resetWreathInput() {
        val current = gameState.value
        gameState.value = current.copy(wreathInput = emptyList())
        wreathAnimState.value = "center"
    }
    fun closeWreathPuzzle() {
        isWreathPuzzleOpen.value = false
        resetWreathInput()
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