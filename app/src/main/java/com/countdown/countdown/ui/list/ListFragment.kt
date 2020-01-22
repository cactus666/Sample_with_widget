package com.countdown.countdown.ui.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.countdown.countdown.R
import com.countdown.countdown.db.Repository
import kotlinx.android.synthetic.main.fragment_list_notes.*

class ListFragment: Fragment(R.layout.fragment_list_notes) {

    private val handler = Handler()
    private lateinit var adapter: AdapterListNote

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redirect_to_create_note.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_createNoteFragment)
        }

        adapter = //AdapterListNote(LayoutInflater.from(context), listOf(Note("qwert", Date(2021, 1,2))))
            AdapterListNote(LayoutInflater.from(context), Repository.getAllNotes())
        list_note.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        handler.post(
            object: Runnable {
                override fun run() {
                    adapter.notifyDataSetChanged()
                    handler.postDelayed(this, 1000)
                }
            }
        )
    }
}