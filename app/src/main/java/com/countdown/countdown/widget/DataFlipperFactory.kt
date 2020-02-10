package com.countdown.countdown.widget

import java.util.ArrayList
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.countdown.countdown.pojo.Note
import com.countdown.countdown.R
import com.countdown.countdown.db.Repository
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class DataFlipperFactory internal constructor(private var context: Context, intent: Intent): RemoteViewsFactory {

    private lateinit var data: ArrayList<Note>
    private var widgetID: Int = 0
    private lateinit var formatDate: SimpleDateFormat

    init {
        Log.d("myLog", "init")
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        formatDate = SimpleDateFormat("dd MM yyyy")
    }

    override fun onCreate() {
        Log.d("myLog", "onCreate")
        data = ArrayList()
    }

    override fun getCount(): Int {
        Log.d("myLog", "getCount")
        return data.size
    }

    override fun getItemId(position: Int): Long {
        Log.d("myLog", "getItemId")
        return position.toLong()
    }

    override fun getLoadingView(): RemoteViews? {
        Log.d("myLog", "getLoadingView")
        return null
    }

    override fun getViewAt(position: Int): RemoteViews {
        Log.d("myLog", "getViewAt, pos = ${position}")
        val view = RemoteViews(context.packageName, R.layout.item_flipper)
        try {
            val diff = data[position].date.time - System.currentTimeMillis()

            val day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            val hour = TimeUnit.HOURS.convert(diff - day * (24 * 60 * 60 * 1000), TimeUnit.MILLISECONDS)
            val minute = TimeUnit.MINUTES.convert(
                diff - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000),
                TimeUnit.MILLISECONDS
            )
            val second = TimeUnit.SECONDS.convert(
                diff - day * (24 * 60 * 60 * 1000) - hour * (60 * 60 * 1000) - minute * (60 * 1000),
                TimeUnit.MILLISECONDS
            )

            if (data[position].title.length > 20) {
                view.setTextViewText(R.id.title, "${data[position].title.substring(0, 20 - 3)}...")
            } else {
                view.setTextViewText(R.id.title, data[position].title)
            }
            view.setTextViewText(R.id.day, day.toString())
            view.setTextViewText(R.id.hour, hour.toString())
            view.setTextViewText(R.id.minutes, minute.toString())
            view.setTextViewText(R.id.seconds, second.toString())

        } catch (ex: Exception) {
            Log.e("err", "drop storage")
        }
        return view
    }


    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        Log.d("myLog", "onDataSetChanged")

        data.clear()
        data.addAll(Repository.getAllNotes())
    }

    override fun onDestroy() {
        Log.d("myLog", "onDestroy")
        data.clear()
    }

}