package com.countdown.countdown.ui.create_note

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.countdown.countdown.R
import com.countdown.countdown.pojo.Note
import kotlinx.android.synthetic.main.fragment_create_note.*
import java.lang.Exception
import java.util.*


class CreateNoteFragment: Fragment(R.layout.fragment_create_note) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener {
             findNavController().popBackStack()
        }
        redirect_to_save.setOnClickListener {
            val newNote = getNote()
            if (newNote != null) {
                val bundle = Bundle()
                bundle.putSerializable("new_note", newNote)
                findNavController().navigate(R.id.action_createNoteFragment_to_saveNoteFragment, bundle)
            }
        }
    }

    private fun getNote(): Note? {
        return if (isDataRight()) {
            generateNote()
        } else {
            null
        }
    }

    private fun isDataRight(): Boolean {
        var result = true
        val date = data_note.text.toString().trim()
        val time = time_note.text.toString().trim()

        // val patternForDate = "[1-31].[1-12].*".toRegex()
       // val patternForTime = "[0-23]:[0-59]".toRegex()
//        patternForDate.matches(date)

        if (name_note.text.toString().trim() == "") {
            //
            result = false
        }

        if (date == "") {
            //
            result = false
        }
        else {
            var day = 0
            var month = 0
            var year = ""
            try {
                day = date.split(".")[0].toInt()
                month = date.split(".")[1].toInt()
                year = date.split(".")[2]
                if ((day in 1..31) && (month in 1..12) && (year.length == 4)) {
                    //
                    result = false
                }
            }catch (ex: Exception) {
                //
                result = false
            }
        }


        if (time == "") {
            //
            result = false
        }
        else {
            var h = 0
            var m = 0
            try {
                h = time.split(":")[0].toInt()
                m = time.split(":")[1].toInt()
                if ((h in 0..23) && (m in 0..59)) {
                    //
                    result = false
                }
            }
            catch(ex: Exception) {
                //
                result = false
            }
        }


        return result
    }

    private fun generateNote(): Note {
        val fieldDataNote = data_note.text.toString()
        val fieldTimeNote = time_note.text.toString()

       // Log.d("generate", "fieldDataNote = ${fieldDataNote}, fieldTimeNote = ${fieldTimeNote}")
        val year = fieldDataNote.split(".")[2].toInt()
        val month = fieldDataNote.split(".")[1].toInt()
        val date = fieldDataNote.split(".")[0].toInt()
        val hrs = fieldTimeNote.split(":")[0].toInt()
        val min = fieldTimeNote.split(":")[1].toInt()

      //  Log.d("generate", "year = ${year}, month = ${month}, date = ${date}, hrs = ${hrs}, min = ${min}")
        return Note(
            name_note.text.toString(),
            Date(year, month, date, hrs, min)
        )
    }

}