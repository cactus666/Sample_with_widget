package com.countdown.countdown.widget

import android.annotation.SuppressLint
import java.util.Arrays
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.content.Intent
import android.app.PendingIntent
import com.countdown.countdown.R
import android.app.AlarmManager
import android.content.ComponentName








class Widget: AppWidgetProvider() {

    val ACTION_NEXT = "com.countdown.countdown.next_note"
    val ACTION_UPDATE = "com.countdown.countdown.update_note"
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
        view.setRemoteAdapter(R.id.adapter_view_flipper, adapter)
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
        Log.d("onReceive", "---: ${intent.action}, time = ${System.currentTimeMillis()}")
        if (intent.action.equals(ACTION_NEXT)) {
            // извлекаем ID экземпляра
            var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
            val extras = intent.extras
            if (extras != null) {
                widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            }
            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                val widgetView = RemoteViews(context.packageName,
                    R.layout.widget
                )
                updateList(context)
                widgetView.showNext(R.id.adapter_view_flipper)
                // Обновляем виджет
                AppWidgetManager.getInstance(context).updateAppWidget(widgetId, widgetView)
            }
        }
        if (intent.action.equals(ACTION_UPDATE)) {
            updateList(context)
        }
    }

    @SuppressLint("ShortAlarm")
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(LOG_TAG, "onEnabled")
        createUpdatingWidget(context!!, 1 * 1000)
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(LOG_TAG, "onDisabled")
        deleteUpdatingWidget(context!!)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds))
    }


    private fun createUpdatingWidget(context: Context, interval: Long = 1 * 1000) {
        val intent = Intent(context, Widget::class.java)
        intent.action = ACTION_UPDATE
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, pIntent)
    }

    private fun deleteUpdatingWidget(context: Context) {
        val intent = Intent(context, Widget::class.java)
        intent.action = ACTION_UPDATE
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent)
    }

    private fun updateList(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, javaClass))
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.adapter_view_flipper)
    }

}