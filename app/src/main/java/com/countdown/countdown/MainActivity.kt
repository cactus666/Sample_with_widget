package com.countdown.countdown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
//import kotlinx.android.synthetic.main.widget.adapter_view_flipper
import kotlinx.android.synthetic.main.widget.next_note
import java.text.SimpleDateFormat
import java.util.*

class MainActivity: AppCompatActivity() {

    private lateinit var adapter: AdapterFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//
//
//        next_note.setOnClickListener {
//            adapter_view_flipper.showNext()
//        }

//        int[] catImages = {R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4,
//            R.drawable.cat5};
//        String catNames[] = {"Барсик", "Рыжик", "Мурзик", "Васька", "Борис"};


//        CustomAdapter adapter = new CustomAdapter(this, catNames, catImages)





//        val myFormat = SimpleDateFormat("dd MM yyyy")
//
//        adapter = AdapterFlipper(LayoutInflater.from(this), listOf(
//            Note(
//                "A",
//                myFormat.parse("23 01 2020")
//            ),
//            Note(
//                "B",
//                myFormat.parse("27 04 2020")
//            ),
//            Note(
//                "C",
//                myFormat.parse("27 04 2022")
//            )
//        ))
//        adapter_view_flipper.adapter = adapter
//        adapter_view_flipper.flipInterval = 3000
//        adapter_view_flipper.isAutoStart = true
//
//        adapter_view_flipper.setFlipInterval(3000)

    }
}
