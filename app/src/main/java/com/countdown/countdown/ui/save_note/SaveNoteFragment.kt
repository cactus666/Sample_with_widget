package com.countdown.countdown.ui.save_note

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.countdown.countdown.R
import com.countdown.countdown.db.Repository
import com.countdown.countdown.pojo.Note
import kotlinx.android.synthetic.main.fragment_save_note.*
import android.content.Context


class SaveNoteFragment: Fragment(R.layout.fragment_save_note) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveNote(arguments!!.getSerializable("new_note") as Note)

        ready.setOnClickListener {
            findNavController().popBackStack(R.id.listFragment, false)
        }
        redirect_to_create_note.setOnClickListener {
            findNavController().popBackStack()
        }
        redirect_to_list.setOnClickListener {
            findNavController().popBackStack(R.id.listFragment, false)
        }
    }

    private fun saveNote(newNote: Note) {
        Repository.addNewNote(newNote)
    }

    override fun onResume() {
        super.onResume()
        hideKeyboardFromFragment(context!!, view!!.rootView)
    }


    private fun hideKeyboardFromFragment(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}