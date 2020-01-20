package com.countdown.countdown

import java.util.Arrays
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import android.content.Intent





class Widget: AppWidgetProvider() {

    val LOG_TAG = "myLogs"
//    private lateinit var formatDate: SimpleDateFormat
//    private lateinit var adapter: AdapterFlipper

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds))

        for (i in appWidgetIds) {
            updateWidget(context, appWidgetManager, i)
        }


//        appWidgetIds?.forEach { id ->
//            val widgetView = RemoteViews(context!!.packageName, R.layout.widget)
//
//            widgetView.set
//            widgetView.setInt(R.id.adapter_view_flipper, "setFlipInterval", 3000)
//            widgetView.setBoolean(R.id.adapter_view_flipper, "setAutoStart", true)
//
//
//
//            widgetView.setTextViewText(R.id.adapter_view_flipper, widgetText)
//            widgetView.setInt(R.id.tv, "setBackgroundColor", widgetColor)
//            appWidgetManager!!.updateAppWidget(id, widgetView)
//        }

    }


    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val view = RemoteViews(context.packageName, R.layout.widget)
        setList(view, context, appWidgetId)
        appWidgetManager.updateAppWidget(appWidgetId, view)
    }



    private fun setList(view: RemoteViews, context: Context, appWidgetId: Int) {
        val adapter = Intent(context, DataFlipperService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        view.setRemoteAdapter(R.id.adapter_view_flipper , adapter)
    }






    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(LOG_TAG, "onEnabled")
//        formatDate = SimpleDateFormat("dd MM yyyy")
//        adapter = AdapterFlipper(
//            LayoutInflater.from(context), listOf(
//                Note(
//                    "A",
//                    formatDate.parse("23 01 2020")
//                ),
//                Note(
//                    "B",
//                    formatDate.parse("27 04 2020")
//                ),
//                Note(
//                    "C",
//                    formatDate.parse("27 04 2022")
//                )
//            ))
//        adapter_view_flipper.adapter = adapter
//        adapter_view_flipper.flipInterval = 3000
//        adapter_view_flipper.isAutoStart = true
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(LOG_TAG, "onDisabled")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }


}