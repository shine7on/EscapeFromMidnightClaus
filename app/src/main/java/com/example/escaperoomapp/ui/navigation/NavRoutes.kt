package com.example.escaperoomapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object StartScreenRoute : NavKey

@Serializable
data object GameScreenRoute : NavKey

@Serializable
data object EndScreenRoute : NavKey
