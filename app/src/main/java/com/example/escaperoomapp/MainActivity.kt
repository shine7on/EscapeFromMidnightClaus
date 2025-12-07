package com.example.escaperoomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

import com.example.escaperoomapp.ui.navigation.StartScreenRoute
import com.example.escaperoomapp.ui.navigation.GameScreenRoute
import com.example.escaperoomapp.ui.navigation.EndScreenRoute
import com.example.escaperoomapp.ui.screen.GameScreen
import com.example.escaperoomapp.ui.screen.EndScreen
import com.example.escaperoomapp.ui.screen.StartScreen

import com.example.escaperoomapp.ui.theme.EscapeRoomAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EscapeRoomAppTheme {
                Scaffold { innerPadding ->
                    NavGraph(modifier = Modifier.padding(innerPadding))
                }

            }
        }
    }
}

@Composable
fun NavGraph(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(StartScreenRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },

        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {

            entry<StartScreenRoute> {
                StartScreen(backStack)
            }

            entry<GameScreenRoute> {
                GameScreen(backStack)
            }

            entry<EndScreenRoute> {
                EndScreen(backStack)
            }
        }
    )
}