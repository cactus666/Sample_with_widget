package com.countdown.countdown

import android.app.Application
import com.countdown.countdown.db.Repository
import io.paperdb.Paper


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        Repository.updateDate()
    }
}