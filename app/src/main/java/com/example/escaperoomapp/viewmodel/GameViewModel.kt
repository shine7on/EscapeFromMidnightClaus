package com.example.escaperoomapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.escaperoomapp.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    // ============================================================
    // CORE GAME STATE
    // ============================================================
    var gameState = mutableStateOf(GameState())
        private set

    private val correctWreathSequence = listOf(
        Direction.RIGHT, Direction.LEFT, Direction.LEFT, Direction.RIGHT, Direction.LEFT
    )


    // ============================================================
    // ZOOM & DIALOG STATES
    // ============================================================
    var isWindowZoomOpen = mutableStateOf(false)
    var isWreathPuzzleOpen = mutableStateOf(false)
    var isPresentZoomOpen = mutableStateOf(false)
    var isShelfZoomOpen = mutableStateOf(false)
    var isDoorZoomOpen = mutableStateOf(false)

    var isPaintingZoomOpen = mutableStateOf(false)
    var isFireplaceZoomOpen = mutableStateOf(false)
    var isLockerZoomOpen = mutableStateOf(false)

    var isCabinetZoomOpen = mutableStateOf(false)
    var isDotPanelZoomOpen = mutableStateOf(false)
    var isBloodDialogOpen = mutableStateOf(false)

    var isWreathFailDialogOpen = mutableStateOf(false)
    var isFireplaceLitDialogOpen = mutableStateOf(false)


    // ============================================================
    // INVENTORY STATE
    // ============================================================
    var selectedItem = mutableStateOf<Item?>(null)
    var lastItemTapTime = mutableStateOf(0L)

    var isItemInspectDialogOpen = mutableStateOf(false)
    var inspectingItem = mutableStateOf<Item?>(null)

    var foundItemDialogOpen = mutableStateOf(false)
    var lastFoundItem = mutableStateOf<Item?>(null)


    // ============================================================
    // FIREPLACE ANIMATION STATE
    // ============================================================
    var fireplaceFrame = mutableStateOf(0)      // 0=center, 1=left, 2=right
    var isFireAnimating = mutableStateOf(false)


    // ============================================================
    // LOCKER INPUT STATE
    // ============================================================
    var lockerInput = mutableStateOf("")
    private val lockerCode = "5324"


    // ============================================================
    // MOVEMENT LOGIC
    // ============================================================
    fun moveLeft() {
        when (gameState.value.currentWall) {
            Wall.WALLCENTER -> setWall(Wall.WALLLEFT)
            Wall.WALLRIGHT -> setWall(Wall.WALLCENTER)
            else -> {}
        }
    }

    fun moveRight() {
        when (gameState.value.currentWall) {
            Wall.WALLCENTER -> setWall(Wall.WALLRIGHT)
            Wall.WALLLEFT -> setWall(Wall.WALLCENTER)
            else -> {}
        }
    }

    private fun setWall(newWall: Wall) {
        // AUTO-CLOSE ALL ZOOMS WHEN CHANGING WALLS
        isWindowZoomOpen.value = false
        isWreathPuzzleOpen.value = false
        isPresentZoomOpen.value = false
        isShelfZoomOpen.value = false
        isDoorZoomOpen.value = false
        isPaintingZoomOpen.value = false
        isFireplaceZoomOpen.value = false
        isLockerZoomOpen.value = false
        isCabinetZoomOpen.value = false
        isDotPanelZoomOpen.value = false
        isBloodDialogOpen.value = false

        // this also stops fire animation if zoom was open
        stopFireAnimation()

        // CHANGE WALL
        gameState.value = gameState.value.copy(currentWall = newWall)
    }


    // ============================================================
    // ZOOM OPEN HELPERS
    // ============================================================
    fun onWindowClicked() { isWindowZoomOpen.value = true }
    fun onWreathClicked() { isWreathPuzzleOpen.value = true }
    fun onPresentClicked() { isPresentZoomOpen.value = true }
    fun onShelfClicked() { isShelfZoomOpen.value = true }
    fun onLockedDoorClicked() { isDoorZoomOpen.value = true }

    fun openPaintingZoom() { isPaintingZoomOpen.value = true }
    fun openFireplaceZoom() { isFireplaceZoomOpen.value = true }
    fun openLockerZoom() { isLockerZoomOpen.value = true }

    fun openCabinetZoom() { isCabinetZoomOpen.value = true }
    fun openDotPanelZoom() { isDotPanelZoomOpen.value = true }
    fun openBloodDialog() { isBloodDialogOpen.value = true }

    fun openFireLitDialog() { isFireplaceLitDialogOpen.value = true }

    var isTreeZoomOpen = mutableStateOf(false)
    fun openTreeZoom() { isTreeZoomOpen.value = true }
    fun closeTreeZoom() { isTreeZoomOpen.value = false }



    // ============================================================
    // ZOOM CLOSE HELPERS
    // ============================================================
    fun closeWindowZoom() { isWindowZoomOpen.value = false }
    fun closePresentZoom() { isPresentZoomOpen.value = false }
    fun closeShelfZoom() { isShelfZoomOpen.value = false }
    fun closeDoorZoom() { isDoorZoomOpen.value = false }

    fun closePaintingZoom() { isPaintingZoomOpen.value = false }
    fun closeFireplaceZoom() { isFireplaceZoomOpen.value = false }
    fun closeLockerZoom() { isLockerZoomOpen.value = false }

    fun closeCabinetZoom() { isCabinetZoomOpen.value = false }
    fun closeDotPanelZoom() { isDotPanelZoomOpen.value = false }
    fun closeBloodDialog() { isBloodDialogOpen.value = false }

    fun closeWreathFailDialog() { isWreathFailDialogOpen.value = false }
    fun closeFireLitDialog() { isFireplaceLitDialogOpen.value = false }

    fun closeInspectDialog() { isItemInspectDialogOpen.value = false }


    // ============================================================
    // FIREPLACE ANIMATION (CLUE SEQUENCE)
    // ============================================================
    fun startFireAnimation() {
        if (isFireAnimating.value) return
        isFireAnimating.value = true

        val puzzleFrames = correctWreathSequence.map {
            when (it) {
                Direction.LEFT -> 1
                Direction.RIGHT -> 2
            }
        }

        val sequence = mutableListOf<Int>()
        for (frame in puzzleFrames) {
            sequence.add(frame)
            sequence.add(0)     // center between each beat
        }

        viewModelScope.launch {
            while (isFireAnimating.value) {

                for (frame in sequence) {
                    fireplaceFrame.value = frame
                    delay(550)
                }

                fireplaceFrame.value = 0
                delay(1300) // loop restart cue
            }
        }
    }

    fun stopFireAnimation() {
        isFireAnimating.value = false
    }


    // ============================================================
    // ITEM INTERACTION
    // ============================================================
    fun addItem(item: Item) {
        val current = gameState.value
        gameState.value = current.copy(
            inventory = current.inventory + item,
            wreathInput = emptyList()
        )
    }

    fun removeItem(item: Item) {
        val current = gameState.value
        gameState.value = current.copy(inventory = current.inventory - item)
    }

    fun hasItem(item: Item): Boolean =
        gameState.value.inventory.contains(item)


    // ============================================================
    // INVENTORY TAP HANDLING
    // ============================================================
    fun onItemTapped(item: Item) {
        val now = System.currentTimeMillis()

        if (now - lastItemTapTime.value < 300) {
            inspectingItem.value = item
            isItemInspectDialogOpen.value = true
            return
        }

        selectedItem.value = item
        lastItemTapTime.value = now
    }


    // ============================================================
    // VERTICAL PUZZLE: WREATH
    // ============================================================
    var wreathAnimState = mutableStateOf("center")

    fun processWreathInput(newInput: List<Direction>) {
        val current = gameState.value
        val item = Item.SnowmanOrnament

        if (newInput == correctWreathSequence) {
            val updatedFlags = current.flags.copy(
                wreathShaken = true,
                windowOpened = true
            )

            gameState.value = current.copy(
                inventory = current.inventory + item,
                wreathInput = emptyList(),
                flags = updatedFlags
            )

            lastFoundItem.value = item
            foundItemDialogOpen.value = true

            wreathAnimState.value = "center"
            return
        }

        if (newInput.size >= correctWreathSequence.size) {
            isWreathFailDialogOpen.value = true
            gameState.value = current.copy(wreathInput = emptyList())
            wreathAnimState.value = "center"
            return
        }

        gameState.value = current.copy(wreathInput = newInput)
    }

    fun resetWreathInput() {
        gameState.value = gameState.value.copy(wreathInput = emptyList())
        wreathAnimState.value = "center"
    }

    fun closeWreathPuzzle() {
        isWreathPuzzleOpen.value = false
        resetWreathInput()
    }

    // ============================================================
    // PUZZLE: PAINTING & 9-DOT PANEL
    // ============================================================
    var dotPatternInput = mutableStateOf<List<Int>>(emptyList())
    var activePattern = mutableStateOf<List<Int>>(emptyList())
    var isDrawing = mutableStateOf(false)

    private val correctDotPattern = listOf(1, 2, 5, 6, 9, 8, 7)

    fun onGestureEnd() {
        isDrawing.value = false

        if (activePattern.value == correctDotPattern) {
            interact(ObjectID.WC_DOT_PANEL) // gives Matchbox
        }

        // Reset regardless
        activePattern.value = emptyList()
    }

    fun onDotDragOver(index: Int) {
        val current = activePattern.value

        // Avoid duplicate dots
        if (current.contains(index)) return

        activePattern.value = current + index
    }


    private fun checkPattern(input: List<Int>) {
        if (input == correctDotPattern) {
            interact(ObjectID.WC_DOT_PANEL)
            gameState.value = gameState.value.copy(
                flags = gameState.value.flags.copy(dotPanelSolved = true)
            )
        } else {
            // Wrong → reset
            dotPatternInput.value = emptyList()
        }
    }


    // ============================================================
    // PUZZLE: LOCKER
    // ============================================================
    fun submitLockerCode() {
        if (lockerInput.value == lockerCode) {
            solveLocker()
            closeLockerZoom()
        } else lockerInput.value = ""
    }

    fun solveLocker() {
        val current = gameState.value
        if (current.flags.lockerOpened) return

        val updated = current.flags.copy(lockerOpened = true)
        addItem(Item.ChainCutter)

        gameState.value = current.copy(flags = updated)
    }


    // ============================================================
    // MAIN OBJECT INTERACTION SYSTEM
    // ============================================================
    fun interact(id: ObjectID) {
        val current = gameState.value

        when (id) {

            // ---------------- WALL CENTER ----------------
            ObjectID.WC_CABINET_UNLOCKED -> {
                if (current.flags.openedShelf) return

                gameState.value = current.copy(
                    flags = current.flags.copy(openedShelf = true)
                )

                val updatedFlags = current.flags.copy(
                    openedShelf = true
                )

                gameState.value = current.copy(
                    inventory = current.inventory + Item.OperaGlass,
                    flags = updatedFlags
                )

                lastFoundItem.value = Item.OperaGlass
                foundItemDialogOpen.value = true

                return
            }

            ObjectID.WC_DOT_PANEL -> {
                if (current.flags.dotPanelSolved) return
                addItem(Item.Matchbox)
                gameState.value = current.copy(
                    flags = current.flags.copy(dotPanelSolved = true)
                )
            }

            ObjectID.WC_DOOR -> {
                if (!hasItem(Item.Key)) return
                removeItem(Item.Key)
                gameState.value = current.copy(
                    flags = current.flags.copy(doorUnlocked = true)
                )
            }

            ObjectID.WC_WREATH_LEFT -> {
                wreathAnimState.value = "left"
                processWreathInput(gameState.value.wreathInput + Direction.LEFT)
            }

            ObjectID.WC_WREATH_RIGHT -> {
                wreathAnimState.value = "right"
                processWreathInput(gameState.value.wreathInput + Direction.RIGHT)
            }


            // ---------------- WALL LEFT ----------------
            ObjectID.WL_FIREPLACE -> {
                openFireplaceZoom()

                if (current.flags.fireplaceLit) return

                if (selectedItem.value == Item.Matchbox) {
                    val newInventory = current.inventory - Item.Matchbox
                    selectedItem.value = null

                    viewModelScope.launch {
                        delay(450)
                        gameState.value = current.copy(
                            flags = current.flags.copy(fireplaceLit = true),
                            inventory = newInventory
                        )
                        openFireLitDialog()
                    }
                }
            }

            ObjectID.WL_ORNAMENT_SHELF -> {
                if (current.flags.ornamentPlaced || current.flags.ornamentShelfOrdered) return

                if (selectedItem.value == Item.SnowmanOrnament) {
                    val newInventory = current.inventory - Item.SnowmanOrnament
                    selectedItem.value = null


                    gameState.value = current.copy(
                            flags = current.flags.copy(ornamentPlaced = true),
                            inventory = newInventory
                        )

                }
            }

            ObjectID.WL_SMALL_LOCKER -> {
                if (current.flags.lockerOpened) return
                if (!current.flags.ornamentShelfOrdered) return
            }


            // ---------------- WALL RIGHT ----------------
            ObjectID.WR_WINDOW -> {
                if (!current.flags.windowOpened) return
                if (!hasItem(Item.OperaGlass)) return
                isWindowZoomOpen.value = true
            }

            ObjectID.WR_PRESENT_BOX -> {
                if (current.flags.presentOpened) return
                // if (!current.flags.lockerOpened) return
                // if (!hasItem(Item.ChainCutter)) return

                removeItem(Item.ChainCutter)
                addItem(Item.Key)

                gameState.value = current.copy(
                    flags = current.flags.copy(presentOpened = true)
                )
            }

            ObjectID.WR_TREE -> {
                if (!current.flags.ornamentShelfOrdered) return
            }
        }
    }
}
