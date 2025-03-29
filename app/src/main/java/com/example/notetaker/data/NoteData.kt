package com.example.notetaker.data

import androidx.compose.ui.graphics.Color

data class NoteData(
    val id: Int = 0,
    val title: String,
    val content: String,
    val color: Color,
    val date: Long
) {
    fun colorToHex(): String {
        val red = (color.red * 255).toInt()
        val green = (color.green * 255).toInt()
        val blue = (color.blue * 255).toInt()
        return String.format("#%02X%02X%02X", red, green, blue)
    }

    companion object {
        fun hexToColor(colorHex: String): Color {
            return try {
                Color(android.graphics.Color.parseColor(colorHex))
            } catch (e: Exception) {
                Color.DarkGray
            }
        }
    }
}