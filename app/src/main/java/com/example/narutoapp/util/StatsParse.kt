package com.example.narutoapp.util

import androidx.compose.ui.graphics.Color
import com.example.narutoapp.ui.theme.TypeDark
import com.example.narutoapp.ui.theme.TypeElectric
import com.example.narutoapp.ui.theme.TypeFighting
import com.example.narutoapp.ui.theme.TypeFire
import com.example.narutoapp.ui.theme.TypeFlying
import com.example.narutoapp.ui.theme.TypeGrass
import com.example.narutoapp.ui.theme.TypeGround
import com.example.narutoapp.ui.theme.TypePoison
import com.example.narutoapp.ui.theme.TypeWater

fun parseTypeToColor(type: String): Color {
    return when(type) {
        "Fire Release" -> TypeFire
        "Wind Release" -> TypeFlying
        "Lightning Release" -> TypeElectric
        "Earth Release" -> TypeGround
        "Water Release" -> TypeWater
        "Wood Release" -> TypeGrass
        "Yin Release" -> TypePoison
        "Yang Release" -> TypeFighting
        "Yin–Yang Release" -> TypeDark
        else -> Color.Black
    }
}