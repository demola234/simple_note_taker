package com.example.notetaker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notetaker.dao.NoteDao
import com.example.notetaker.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}