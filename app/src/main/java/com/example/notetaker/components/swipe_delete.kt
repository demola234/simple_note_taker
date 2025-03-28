package com.example.notetaker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.notetaker.data.NoteData
import com.example.notetaker.screen.NoteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteNoteItem(
    note: NoteData,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart ||
                dismissValue == SwipeToDismissBoxValue.StartToEnd
            ) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = Color(0xFFE53935)  // Lighter red that contrasts well with white
            val direction = dismissState.dismissDirection

            // Different background based on swipe direction
            val alignment = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> androidx.compose.ui.Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> androidx.compose.ui.Alignment.CenterEnd
                else -> androidx.compose.ui.Alignment.Center
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp)),

                contentAlignment = alignment
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        content = {
            NoteItem(note = note)
        }
    )
}