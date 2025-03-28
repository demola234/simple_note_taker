package com.example.notetaker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notetaker.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table ORDER BY date DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNoteById(note: NoteEntity)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("UPDATE notes_table SET title = :title, content = :content, color = :colorHex, date = :date WHERE id = :id")
    suspend fun updateNoteById(id: Int, title: String, content: String, colorHex: String, date: Long)
}