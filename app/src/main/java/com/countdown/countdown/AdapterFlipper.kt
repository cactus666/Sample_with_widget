package com.countdown.countdown

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_flipper.view.*
import java.util.concurrent.TimeUnit


class AdapterFlipper(private val inflater: LayoutInflater, private val mData: List<Note>): BaseAdapter() {

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolderWidget
        var convertView = convertView

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_flipper, null)

            viewHolder = ViewHolderWidget()
            viewHolder.title = convertView.title
            //viewHolder.year = convertView.year
            viewHolder.day = convertView.day
            viewHolder.hour = convertView.hour
            viewHolder.minute = convertView.minutes
            viewHolder.second = convertView.seconds

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolderWidget
        }

        viewHolder.title.text = mData[position].title

        val diff = mData[position].date.time - System.currentTimeMillis()

        val day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        val hour = TimeUnit.HOURS.convert(diff - day * (24 * 60 * 60 * 1000), TimeUnit.MILLISECONDS)
        val minute = TimeUnit.MINUTES.convert(diff - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000), TimeUnit.MILLISECONDS)
        val second = TimeUnit.SECONDS.convert(diff - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000) - minute * (60 * 1000), TimeUnit.MILLISECONDS)

        viewHolder.day.text = day.toString()
        viewHolder.hour.text = hour.toString()
        viewHolder.minute.text = minute.toString()
        viewHolder.second.text = second.toString()

        return convertView!!
    }

}
