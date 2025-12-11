package com.example.escaperoomapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.escaperoomapp.model.*
import kotlinx.coroutines.Job
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

    var isOperaZoomOpen = mutableStateOf(false)
    fun closeOperaZoom() {
        isOperaZoomOpen.value = false
    }


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

        // --- CLOSE EVERY ZOOM/DIALOG WHEN MOVING WALLS ---
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
        isWreathFailDialogOpen.value = false
        isFireplaceLitDialogOpen.value = false

        isTreeZoomOpen.value = false
        isOperaZoomOpen.value = false
        isItemInspectDialogOpen.value = false

        stopFireAnimation()

        // --- SWITCH WALL ---
        gameState.value = gameState.value.copy(currentWall = newWall)
    }

    fun isGameCompleted(): Boolean {
        val f = gameState.value.flags

        return f.doorUnlocked &&
                f.presentOpened &&
                f.ornamentPlaced &&
                f.fireplaceLit &&
                f.dotPanelSolved &&
                f.lockerOpened &&
                f.windowOpened
    }

    fun resetGame() {
        gameState.value = GameState()
        // Reset full game state
        gameState.value = GameState()

        // Reset all UI/zoom states
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
        isWreathFailDialogOpen.value = false
        isFireplaceLitDialogOpen.value = false
        isTreeZoomOpen.value = false
        isOperaZoomOpen.value = false
        isItemInspectDialogOpen.value = false

        // Reset inventory/UI selections
        selectedItem.value = null
        inspectingItem.value = null
        foundItemDialogOpen.value = false
        lastFoundItem.value = null

        // Reset puzzles
        activePattern.value = emptyList()
        isDrawing.value = false
        wreathAnimState.value = "center"

        // Stop animation
        stopFireAnimation()
    }



    // ============================================================
    // ZOOM OPEN HELPERS
    // ============================================================
    fun onWindowClicked() {
        isWindowZoomOpen.value = true
    }

    fun onWreathClicked() {
        isWreathPuzzleOpen.value = true
    }

    fun onLockedDoorClicked() {
        isDoorZoomOpen.value = true
    }

    fun openPaintingZoom() {
        isPaintingZoomOpen.value = true
    }

    fun openFireplaceZoom() {
        isFireplaceZoomOpen.value = true
    }

    fun openLockerZoom() {
        isLockerZoomOpen.value = true
    }

    fun openCabinetZoom() {
        isCabinetZoomOpen.value = true
    }

    fun openDotPanelZoom() {
        isDotPanelZoomOpen.value = true
    }

    fun openBloodDialog() {
        isBloodDialogOpen.value = true
    }

    var isTreeZoomOpen = mutableStateOf(false)
    fun openTreeZoom() {
        isTreeZoomOpen.value = true
    }

    fun closeTreeZoom() {
        isTreeZoomOpen.value = false
    }


    // ============================================================
    // ZOOM CLOSE HELPERS
    // ============================================================
    fun closeWindowZoom() {
        isWindowZoomOpen.value = false
    }

    fun closePresentZoom() {
        isPresentZoomOpen.value = false
    }

    fun closeShelfZoom() {
        isShelfZoomOpen.value = false
    }

    fun closeDoorZoom() {
        isDoorZoomOpen.value = false

        if (isGameCompleted()) {
            gameState.value = gameState.value.copy(gameWon = true)
        }
    }

    fun closePaintingZoom() {
        isPaintingZoomOpen.value = false
    }

    fun closeFireplaceZoom() {
        isFireplaceZoomOpen.value = false
    }

    fun closeLockerZoom() {
        if (!gameState.value.flags.lockerOpened) {
            lockerInput.value = ""
        }

        isLockerZoomOpen.value = false
    }

    fun closeCabinetZoom() {
        isCabinetZoomOpen.value = false
    }

    fun closeDotPanelZoom() {
        isDotPanelZoomOpen.value = false
    }

    fun closeBloodDialog() {
        isBloodDialogOpen.value = false
    }

    fun closeWreathFailDialog() {
        isWreathFailDialogOpen.value = false
    }

    fun closeInspectDialog() {
        isItemInspectDialogOpen.value = false
    }


    // ============================================================
    // FIREPLACE ANIMATION (CLUE SEQUENCE)
    // ============================================================
    private var fireJob: Job? = null

    fun startFireAnimation() {
        if (fireJob?.isActive == true) return

        isFireAnimating.value = true
        fireplaceFrame.value = 0

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

        fireJob = viewModelScope.launch {
            delay(200)

            while (isFireAnimating.value) {
                for (frame in sequence) {
                    fireplaceFrame.value = frame
                    delay(550)
                }

                fireplaceFrame.value = 0
                delay(1300)
            }
        }
    }

    fun stopFireAnimation() {
        isFireAnimating.value = false
        fireJob?.cancel()
        fireJob = null
        fireplaceFrame.value = 0
    }
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
                wreathShaken = true
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

        gameState.value = current.copy(
            inventory = current.inventory + Item.Knife,
            flags = current.flags.copy(lockerOpened = true)
        )

        lastFoundItem.value = Item.Knife
        foundItemDialogOpen.value = true

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

                val newInventory = current.inventory + Item.Matchbox

                gameState.value = current.copy(
                    inventory = newInventory,
                    flags = current.flags.copy(dotPanelSolved = true)
                )

                lastFoundItem.value = Item.Matchbox
                foundItemDialogOpen.value = true

                isDotPanelZoomOpen.value = false
                isCabinetZoomOpen.value = false

            }

            ObjectID.WC_DOOR -> {
                if (selectedItem.value == Item.Key) {

                    val newInventory = current.inventory - Item.Key
                    selectedItem.value = null

                    gameState.value = current.copy(
                        inventory = newInventory,
                        flags = current.flags.copy(doorUnlocked = true)
                    )
                }
            }

            ObjectID.WC_WREATH_LEFT -> {
                if (current.flags.wreathShaken) return

                wreathAnimState.value = "left"
                processWreathInput(gameState.value.wreathInput + Direction.LEFT)
            }

            ObjectID.WC_WREATH_RIGHT -> {
                if (current.flags.wreathShaken) return

                wreathAnimState.value = "right"
                processWreathInput(gameState.value.wreathInput + Direction.RIGHT)
            }


            // ---------------- WALL LEFT ----------------
            ObjectID.WL_FIREPLACE -> {
                openFireplaceZoom()

                if (current.flags.fireplaceLit) return

                val newInventory = current.inventory - Item.Matchbox
                selectedItem.value = null

                viewModelScope.launch {
                    delay(450)
                    gameState.value = current.copy(
                        flags = current.flags.copy(fireplaceLit = true),
                        inventory = newInventory
                    )
                }
            }

            ObjectID.WL_ORNAMENT_SHELF -> {
                if (current.flags.ornamentPlaced) return

                if (selectedItem.value == Item.SnowmanOrnament) {
                    val newInventory = current.inventory - Item.SnowmanOrnament
                    selectedItem.value = null

                    gameState.value = current.copy(
                        flags = current.flags.copy(ornamentPlaced = true, windowOpened = true),
                        inventory = newInventory
                    )
                }
            }

            // ---------------- WALL RIGHT ----------------
            ObjectID.WR_WINDOW -> {

                if (!current.flags.windowOpened) return

                if (isWindowZoomOpen.value && selectedItem.value == Item.OperaGlass) {
                    isWindowZoomOpen.value = false
                    isOperaZoomOpen.value = true
                    return
                }

                if (!isWindowZoomOpen.value && !isOperaZoomOpen.value) {
                    isWindowZoomOpen.value = true
                    return
                }

                return
            }


            ObjectID.WR_PRESENT_BOX -> {

                if (current.flags.presentOpened) {
                    isPresentZoomOpen.value = true
                    return
                }

                isPresentZoomOpen.value = true

                if (selectedItem.value == Item.Knife) {
                    val newInventory = current.inventory - Item.Knife + Item.Key

                    gameState.value = current.copy(
                        inventory = newInventory,
                        flags = current.flags.copy(presentOpened = true)
                    )

                    lastFoundItem.value = Item.Key
                    foundItemDialogOpen.value = true
                }
            }


            ObjectID.WR_TREE -> {
                openTreeZoom()
            }

            ObjectID.WL_SMALL_LOCKER -> return
        }
    }
}
