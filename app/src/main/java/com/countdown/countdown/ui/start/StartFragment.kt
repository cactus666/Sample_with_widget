package com.countdown.countdown.ui.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.countdown.countdown.R
import com.countdown.countdown.db.Repository
import kotlinx.android.synthetic.main.fragment_first_open.*


class StartFragment: Fragment(R.layout.fragment_first_open) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Repository.isFirstSignIn()) {
            findNavController().navigate(R.id.action_startFragment_to_listFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigate_to_list.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_listFragment)
        }
    }
}