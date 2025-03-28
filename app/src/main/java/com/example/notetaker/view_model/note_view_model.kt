package com.example.notetaker.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

import androidx.lifecycle.ViewModel
import com.example.notetaker.data.NoteData
import com.example.notetaker.model.NoteModel

class NoteViewModel : ViewModel() {
    val titleState = mutableStateOf("")
    val contentState = mutableStateOf("")
    val errorState = mutableStateOf<String?>(null)

    private val _notesList = mutableStateListOf<NoteData>()
    val notesList: List<NoteData> = _notesList

    private val noteColors = listOf(
        Color(0xFF424242),
        Color(0xFF3949AB),
        Color(0xFF1E88E5),
        Color(0xFF00897B),
        Color(0xFF7CB342),
        Color(0xFF5E35B1)
    )

    init {
        loadNotes()
    }

    private fun loadNotes() {
        _notesList.clear()
        _notesList.addAll(NoteModel.notes)
    }

    fun addNote() {
        when {
            titleState.value.isBlank() -> {
                errorState.value = "Title cannot be blank"
            }

            contentState.value.isBlank() -> {
                errorState.value = "Content cannot be blank"
            }

            else -> {
                errorState.value = null
                val note = NoteData(
                    title = titleState.value,
                    content = contentState.value,
                    color = noteColors.random(),
                    date = System.currentTimeMillis()
                )

                NoteModel.notes.add(
                    note
                )
                _notesList.add(note)

                clearInputs()
            }
        }
    }

    fun deleteNote(note: NoteData) {
        NoteModel.notes.remove(note)
        _notesList.remove(note)
    }

    fun clearAllNotes() {
        NoteModel.notes.clear()
        _notesList.clear()
    }

    fun clearInputs() {
        titleState.value = ""
        contentState.value = ""
    }

    fun updateNote(note: NoteData) {
        val index = NoteModel.notes.indexOf(note)
        NoteModel.notes[index] = note.copy(
            title = titleState.value,
            content = contentState.value,
            color = note.color,
            date = System.currentTimeMillis()
        )
        _notesList[index] = note.copy(
            title = titleState.value,
            content = contentState.value,
            color = note.color,
            date = System.currentTimeMillis()
        )
        clearInputs()
    }

    fun updateNoteColor(note: NoteData, color: Color) {
        val index = NoteModel.notes.indexOf(note)
        NoteModel.notes[index] = note.copy(
            title = note.title,
            content = note.content,
            color = color,
            date = System.currentTimeMillis()
        )
        _notesList[index] = note.copy(
            title = note.title,
            content = note.content,
            color = color,
            date = System.currentTimeMillis()
        )
        clearInputs()
    }
}