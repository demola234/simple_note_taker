package com.example.notetaker.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notetaker.components.NoteInput
import com.example.notetaker.components.SwipeToDeleteNoteItem
import com.example.notetaker.model.NoteModel
import com.example.notetaker.data.NoteData
import com.example.notetaker.model.NoteModel.Companion.notes
import com.example.notetaker.view_model.NoteViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NoteScreen(
    viewModel: NoteViewModel = viewModel ()
) {
    val primary = Color(0xFF6200EE)
    val secondary = Color(0xFF03DAC5)


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Note Taker")
                },
                actions = {
                    if (viewModel.notesList.isNotEmpty()) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Delete,
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
                NoteInput(
                    text = viewModel.titleState.value,
                    onTextChange = { newValue ->
                        viewModel.titleState.value = newValue
                    },
                    color = Color.White,
                    label = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    onImeAction = {

                    },
                    imeAction = androidx.compose.ui.text.input.ImeAction.Next,
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
                        .padding(10.dp),
                    onImeAction = {

                    },
                    imeAction = androidx.compose.ui.text.input.ImeAction.Next,
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
                    items(viewModel.notesList.size) { index ->
                        val note = viewModel.notesList[index]
                        val isNoteVisible = remember { mutableStateOf(true) }
                        NoteItem(note)
                    }
                }
            }
        }
    }
}


@Composable
fun NoteItem(note: NoteData) {
    Surface(
        color = note.color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = note.title,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(
                    text = note.content,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(3.dp)
                    .background(note.color)
            )
        }
    }
}