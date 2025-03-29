package com.example.notetaker.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notetaker.components.EditNoteDialog
import com.example.notetaker.components.NoteInput
import com.example.notetaker.data.NoteData
import com.example.notetaker.view_model.NoteViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = "spec:width=1080px,height=2340px,dpi=440")
@Composable

fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val primary = Color(0xFF6200EE)
    val secondary = Color(0xFF03DAC5)
    val notes = viewModel.notes.collectAsState()

    var showEditDialog = remember { mutableStateOf(false) }
    var noteToEdit = remember { mutableStateOf<NoteData?>(null) }

    // Show edit dialog if state is true
    if (showEditDialog.value && noteToEdit.value != null) {
        EditNoteDialog(
            note = noteToEdit.value!!,
            colors = viewModel.noteColors,
            onDismiss = { showEditDialog.value = false },
            onSave = { updatedNote ->
                viewModel.updateNote(updatedNote)
                showEditDialog.value = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Note Taker")
                },
                actions = {
                    if (notes.value.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                                .clickable {
                                    viewModel.clearAllNotes()
                                }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primary,
                    titleContentColor = Color.White
                )

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                AnimatedVisibility(visible = viewModel.errorState.value != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFEBEE))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = viewModel.errorState.value ?: "",
                                color = Color(0xFFB71C1C)
                            )
                            TextButton(
                                onClick = { viewModel.dismissError() }
                            ) {
                                Text("Dismiss", color = primary)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                NoteInput(
                    text = viewModel.titleState.value,
                    onTextChange = { newValue ->
                        viewModel.titleState.value = newValue
                    },
                    color = Color.White,
                    label = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onImeAction = {

                    },
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(10.dp))

                NoteInput(
                    text = viewModel.contentState.value,
                    onTextChange = { newValue ->
                        viewModel.contentState.value = newValue
                    },
                    color = Color.White,
                    label = "Content ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onImeAction = {

                    },
                    imeAction = ImeAction.Next,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        viewModel.addNote()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Save")
                }


                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                ) {
                    items(notes.value.size) { index ->

                        var isNoteVisible = remember { mutableStateOf(true) }

                        AnimatedVisibility(
                            visible = isNoteVisible.value,
                            exit = shrinkHorizontally(
                                animationSpec = tween(durationMillis = 300)
                            ) + fadeOut()
                        ) {
                            NoteItem(notes.value[index],
                                onDelete = {
                                isNoteVisible.value = false
                                viewModel.deleteNote(notes.value[index])
                            }, onEdit =
                                 {
                                     noteToEdit.value = notes.value[index]
                                     showEditDialog.value = true


                                },
                                )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: NoteData, onDelete: () -> Unit, onEdit: () -> Unit) {
    Surface(
        color = note.color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(shape = RoundedCornerShape(10.dp))

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Content column (takes most space)
            Column(
                modifier = Modifier
                    .weight(1f)  // Takes available space
                    .padding(end = 8.dp)  // Gives space between content and icon
            ) {
                Text(
                    text = note.title,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = note.content,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
    Row {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .padding(4.dp)
                .clickable{
                    onEdit()
                }
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .padding(4.dp)
                .clickable {
                    onDelete()
                }
        )
    }
        }
    }
}