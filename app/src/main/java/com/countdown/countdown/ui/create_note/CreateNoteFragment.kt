package com.countdown.countdown.ui.create_note

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.countdown.countdown.R
import com.countdown.countdown.pojo.Note
import kotlinx.android.synthetic.main.fragment_create_note.*
import java.lang.Exception
import java.text.SimpleDateFormat
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
            selectWarningField(name_note)
            result = false
        }
        else {
            selectNormalField(name_note)
        }

        if (date == "") {
            selectWarningField(data_note)
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
                    selectNormalField(data_note)
                }
                else {
                    selectWarningField(data_note)
                    data_note.setText("")
                    //data_note.hint = "ВВедите дату по следующему шаблону: день.месяц.год"
                    result = false
                }
            }catch (ex: Exception) {
                selectWarningField(data_note)
                data_note.setText("")
                //data_note.hint = "ВВедите дату по следующему шаблону: день.месяц.год"
                result = false
            }
        }


        if (time == "") {
            selectWarningField(time_note)
            result = false
        }
        else {
            var h = 0
            var m = 0
            try {
                h = time.split(":")[0].toInt()
                m = time.split(":")[1].toInt()
                if ((h in 0..23) && (m in 0..59)) {
                    selectNormalField(time_note)
                }
                else {
                    selectWarningField(time_note)
                    time_note.setText("")
                    //time_note.hint = "ВВедите время  по следующему шаблону: часы.минуты"
                    result = false
                }
            }
            catch(ex: Exception) {
                selectWarningField(time_note)
                time_note.setText("")
                //time_note.hint = "ВВедите время  по следующему шаблону: часы.минуты"
                result = false
            }
        }


        return result
    }

    private fun checkActualDate(date: Date): Boolean {
        return date.time > System.currentTimeMillis()
    }

    private fun generateNote(): Note?{
        val fieldDataNote = data_note.text.toString()
        val fieldTimeNote = time_note.text.toString()

       // Log.d("generate", "fieldDataNote = ${fieldDataNote}, fieldTimeNote = ${fieldTimeNote}")
        val year = fieldDataNote.split(".")[2].toInt()
        val month = fieldDataNote.split(".")[1].toInt()
        val date = fieldDataNote.split(".")[0].toInt()
        val hrs = fieldTimeNote.split(":")[0].toInt()
        val min = fieldTimeNote.split(":")[1].toInt()

        val format = SimpleDateFormat("dd.MM.yyyy")
      //  Log.d("generate", "year = ${year}, month = ${month}, date = ${date}, hrs = ${hrs}, min = ${min}")
       // Log.d("generate", "res - ${Date(year, month, date, hrs, min).time}, ${format.format(Date(year - 1900, month - 1, date, hrs, min).time)}")

        return if (checkActualDate(Date(year - 1900, month - 1, date, hrs, min))) {
            Note(
                name_note.text.toString(),
                Date(year - 1900, month - 1, date, hrs, min)
            )
        }
        else {
            selectWarningField(data_note)
            selectWarningField(time_note)
            null
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun selectWarningField(field: EditText) {
        field.setBackgroundResource(R.drawable.background_field_create_note_error)
        field.setTextColor(ContextCompat.getColor(context!!, R.color.color_warning))
    }

    @SuppressLint("ResourceAsColor")
    private fun selectNormalField(field: EditText) {
        field.setBackgroundResource(R.drawable.background_description_first_open)
        field.setTextColor(ContextCompat.getColor(context!!, R.color.color_text))
    }

}