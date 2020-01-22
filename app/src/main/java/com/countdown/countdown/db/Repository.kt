package com.countdown.countdown.db

import com.countdown.countdown.pojo.Note
import io.paperdb.Paper

object Repository {

    private const val keyToNotes = "notes"

    fun isFirstSignIn(): Boolean {
        return Paper.book().read(keyToNotes, listOf<Note>()).isEmpty()
    }

    fun addNewNote(newNote: Note) {
        val list = Paper.book().read(keyToNotes, ArrayList<Note>())
        list.add(newNote)
        Paper.book().write(keyToNotes, list)
    }

    fun getAllNotes(): List<Note> {
        return Paper.book().read(keyToNotes, listOf<Note>())
    }

}