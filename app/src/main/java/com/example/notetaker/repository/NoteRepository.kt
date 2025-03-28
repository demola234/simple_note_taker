package com.example.notetaker.repository

import com.example.notetaker.dao.NoteDao
import com.example.notetaker.data.NoteData
import com.example.notetaker.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    val allNotes: Flow<List<NoteData>> = noteDao.getAllNotes().map { entityList ->
        entityList.map { entity ->
            NoteData(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                color = NoteData.hexToColor(entity.color),
                date = entity.date
            )
        }
    }

    suspend fun insertNote(note: NoteData): Long {
        val entity = NoteEntity(
            title = note.title,
            content = note.content,
            color = note.colorToHex(),
            date = note.date
        )
        return noteDao.insertNote(entity)
    }

    // Update an existing note
    suspend fun updateNote(note: NoteData) {
        val entity = NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
            color = note.colorToHex(),
            date = note.date
        )
        noteDao.updateNote(entity)
    }

    // Delete a note
    suspend fun deleteNote(noteData: NoteData) {
        val entity = NoteEntity(
            id = noteData.id,
            title = noteData.title,
            content = noteData.content,
            color = noteData.colorToHex(),
            date = noteData.date
        )
        noteDao.deleteNoteById(entity)
    }

    // Delete all notes
    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    // Get a note by ID
    suspend fun getNoteById(id: Int): NoteData? {
        val note = noteDao.getNoteById(id) ?: return null

        return NoteData(
            id = note.id,
            title = note.title,
            content = note.content,
            color = NoteData.hexToColor(note.color),
            date = note.date
        )
    }

    // Update a note by ID with specific fields
    suspend fun updateNoteById(id: Int, title: String, content: String, colorHex: String, date: Long) {
        noteDao.updateNoteById(id, title, content, colorHex, date)
    }
}