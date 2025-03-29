package com.example.notetaker.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

import androidx.lifecycle.*
import com.example.notetaker.data.NoteData
import com.example.notetaker.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    val titleState = mutableStateOf("")
    val contentState = mutableStateOf("")
    val errorState = mutableStateOf<String?>(null)

    private val _notes = MutableStateFlow<List<NoteData>>(emptyList())
    val notes: StateFlow<List<NoteData>> = _notes.asStateFlow()

    private val noteIds = mutableMapOf<NoteData, Int>()
    val selectedNoteForColorEdit = mutableStateOf<NoteData?>(null)
    val isColorDialogVisible = mutableStateOf(false)
    val selectedNoteForEdit = mutableStateOf<NoteData?>(null)
    val isEditDialogVisible = mutableStateOf(false)



     val noteColors = listOf(
        Color(0xFF424242),
        Color(0xFF3949AB),
        Color(0xFF1E88E5),
        Color(0xFF00897B),
        Color(0xFF7CB342),
        Color(0xFF5E35B1)
    )

    init {
        viewModelScope.launch {
            repository.allNotes.collectLatest { notesList ->
                _notes.value = notesList
            }
        }
    }

    fun addNote() {
        try {
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

                viewModelScope.launch {
                    val id = repository.insertNote(note)
                    noteIds[note] = id.toInt()
                }

                clearInputs()
            }
        }
        } catch (e: Exception) {
            errorState.value = "An error occurred while adding the note. ${e.message}"
        }
    }

    fun deleteNote(note: NoteData) {
       viewModelScope.launch(Dispatchers.IO) {
           try {
               repository.deleteNote(note)
            } catch (e: Exception) {
                errorState.value = "An error occurred while deleting the note. ${e.message}"
            }
        }
    }

    fun clearAllNotes() {
       viewModelScope.launch(Dispatchers.IO) {
           try {
               repository.deleteAllNotes()
           } catch (e: Exception) {
               errorState.value = "An error occurred while clearing all notes. ${e.message}"
           }
       }
    }

    fun clearInputs() {
        titleState.value = ""
        contentState.value = ""
    }

    fun dismissError() {
        errorState.value = null
    }

    fun showColorDialog(note: NoteData) {
        selectedNoteForColorEdit.value = note
        isColorDialogVisible.value = true
    }

    fun hideEditDialog() {
        isEditDialogVisible.value = false
    }

    fun hideColorDialog() {
        isColorDialogVisible.value = false
    }

    fun showEditDialog(note: NoteData) {
        selectedNoteForEdit.value = note
        isEditDialogVisible.value = true
    }


    fun updateNote(note: NoteData) {
       viewModelScope.launch(Dispatchers.IO) {
           try {
               repository.updateNote(note)
           } catch (e: Exception) {
               errorState.value = "An error occurred while updating the note. ${e.message}"
           }
       }
        clearInputs()
    }

    fun updateNoteColor(newColor: Color) {
       viewModelScope.launch(Dispatchers.IO) {
            try {
                val note = selectedNoteForColorEdit.value ?: return@launch

                val updatedNote = note.copy(color = newColor)
                repository.updateNote(updatedNote)
                hideColorDialog()
            } catch (e: Exception) {
                errorState.value = "An error occurred while updating the note color. ${e.message}"
            }
        }
        clearInputs()
    }
}