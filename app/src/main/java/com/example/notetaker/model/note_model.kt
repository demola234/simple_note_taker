package com.example.notetaker.model

import androidx.compose.ui.graphics.Color
import com.example.notetaker.data.NoteData
import java.time.format.DateTimeFormatter


class NoteModel {
    companion object {
        val notes = mutableListOf(
            NoteData(

                title = "Note 1",
                content = "This is the content of note 1",
                color = Color.Red,
                date = System.currentTimeMillis()
            ),
            NoteData(

                title = "Note 2",
                content = "This is the content of note 2",
                color = Color(0xff285172),
                date = System.currentTimeMillis()
            ),
            NoteData(

                title = "Note 3",
                content = "This is the content of note 3",
                color = Color(0xff194837),
                date = System.currentTimeMillis()
            ),
            NoteData(

                title = "Note 4",
                content = "This is the content of note 4",
                color = Color(0xffc252c0),
                date = System.currentTimeMillis()
            ),
            NoteData(

                title = "Note 5",
                content = "This is the content of note 5",
                color = Color(0xffd3a15f),
                date = System.currentTimeMillis()
            ),
            NoteData(

                title = "Note 6",
                content = "This is the content of note 6",
                color = Color(0xff87c934),
                date = System.currentTimeMillis()
            ),
        )
    }
}