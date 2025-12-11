package com.example.escaperoomapp.model

sealed class Item {
    object OperaGlass : Item()
    object Matchbox : Item()
    object SnowmanOrnament : Item()
    object Knife : Item()
    object Key : Item()
}