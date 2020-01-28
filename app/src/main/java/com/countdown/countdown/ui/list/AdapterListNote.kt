package com.countdown.countdown.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.countdown.countdown.R
import com.countdown.countdown.pojo.Note
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class AdapterListNote(private val inflater: LayoutInflater, private var mData: List<Note>) : RecyclerView.Adapter<AdapterListNote.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_notes_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = mData[position].title

        val format = SimpleDateFormat("dd.MM.yyyy")
        Log.d("date", "${format.format(mData[position].date.time)}, now = ${format.format(System.currentTimeMillis())}")
        val diff = mData[position].date.time - System.currentTimeMillis()

        val day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        val hour = TimeUnit.HOURS.convert(diff - day * (24 * 60 * 60 * 1000), TimeUnit.MILLISECONDS)
        val minute = TimeUnit.MINUTES.convert(diff - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000), TimeUnit.MILLISECONDS)
        val second = TimeUnit.SECONDS.convert(diff - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000) - minute * (60 * 1000), TimeUnit.MILLISECONDS)

        viewHolder.day.text = day.toString()
        viewHolder.hour.text = hour.toString()
        viewHolder.minute.text = minute.toString()
        viewHolder.second.text = second.toString()
    }

    fun setData(newData: List<Note>) {
        mData = newData
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title_note)
        var day: TextView = itemView.findViewById(R.id.days)
        var hour: TextView = itemView.findViewById(R.id.hours)
        var minute: TextView = itemView.findViewById(R.id.minutes)
        var second: TextView = itemView.findViewById(R.id.seconds)
    }

}


