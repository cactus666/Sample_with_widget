package com.countdown.countdown.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.content.Intent
import android.app.PendingIntent
import com.countdown.countdown.R
import android.content.ComponentName
import android.os.CountDownTimer


class Widget: AppWidgetProvider() {

    companion object {
        const val ACTION_NEXT = "com.countdown.countdown.next_note"
        const val ACTION_UPDATE = "com.countdown.countdown.update_note"
    }

    private var countDownTimer: CountDownTimer? = null

    @SuppressLint("ShortAlarm")
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        getCountdown(context)!!.start()
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        getCountdown(context)!!.start()
        for (i in appWidgetIds) {
            updateWidget(context, appWidgetManager, i)
        }
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

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }




    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val view = RemoteViews(context.packageName, R.layout.widget)
        setList(view, context, appWidgetId)
        setListenerOnNextArr(context, view, appWidgetId)
        appWidgetManager.updateAppWidget(appWidgetId, view)
    }

    private fun setList(view: RemoteViews, context: Context, appWidgetId: Int) {
        val adapter = Intent(context, DataFlipperService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        view.setRemoteAdapter(R.id.adapter_view_flipper, adapter)
    }

    private fun setListenerOnNextArr(context: Context, widgetView: RemoteViews, widgetId: Int) {
        val nextIntent = Intent(context, Widget::class.java)
        nextIntent.action = ACTION_NEXT
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        val pIntent = PendingIntent.getBroadcast(context, widgetId, nextIntent, 0)
        widgetView.setOnClickPendingIntent(R.id.next_note, pIntent)
    }

    private fun getCountdown(context: Context): CountDownTimer? {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val intent = Intent(context, Widget::class.java)
                intent.action = ACTION_UPDATE
                val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                pIntent.send()
            }
            override fun onFinish() {
                countDownTimer!!.start()
            }
        }
        return countDownTimer
    }

    private fun updateList(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, javaClass))
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.adapter_view_flipper)
    }

}