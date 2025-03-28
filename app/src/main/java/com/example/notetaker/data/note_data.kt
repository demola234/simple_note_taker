package com.example.notetaker.data

import androidx.compose.ui.graphics.Color
import java.time.format.DateTimeFormatter
import java.util.UUID


data class NoteData(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val color: Color,
    val date: Long = System.currentTimeMillis(),
)