package com.countdown.countdown

import java.util.Arrays
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.content.Intent
import android.app.PendingIntent



class Widget: AppWidgetProvider() {

    val ACTION_NEXT = "com.countdown.countdown.next_note"
    val LOG_TAG = "myLogs"

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds))

        for (i in appWidgetIds) {
            updateWidget(context, appWidgetManager, i)
        }

    }


    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val view = RemoteViews(context.packageName, R.layout.widget)
        setList(view, context, appWidgetId)
        setListener(context, view, appWidgetId)
        appWidgetManager.updateAppWidget(appWidgetId, view)
    }

    private fun setList(view: RemoteViews, context: Context, appWidgetId: Int) {
        val adapter = Intent(context, DataFlipperService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        view.setRemoteAdapter(R.id.adapter_view_flipper , adapter)
    }

    private fun setListener(context: Context, widgetView: RemoteViews, widgetId: Int) {
        val nextIntent = Intent(context, Widget::class.java)
        nextIntent.action = ACTION_NEXT
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        val pIntent = PendingIntent.getBroadcast(context, widgetId, nextIntent, 0)
        widgetView.setOnClickPendingIntent(R.id.next_note, pIntent)
    }


    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action.equals(ACTION_NEXT)) {
            // извлекаем ID экземпляра
            var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
            val extras = intent.extras
            if (extras != null) {
                widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            }
            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                val widgetView = RemoteViews(context.packageName, R.layout.widget)
                widgetView.showNext(R.id.adapter_view_flipper)
                // Обновляем виджет
                AppWidgetManager.getInstance(context).updateAppWidget(widgetId, widgetView)
            }
        }
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