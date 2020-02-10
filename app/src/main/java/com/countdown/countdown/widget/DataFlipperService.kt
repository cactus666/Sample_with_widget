package com.countdown.countdown.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService

@SuppressLint("Registered")
class DataFlipperService: RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        Log.d("myLog", "onGetViewFactory with id = ${intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)}")
        return DataFlipperFactory(applicationContext, intent)
    }

}