package com.kh.mo.shopyapp.ui.base

import android.app.Application
import com.yariksoffice.lingver.Lingver

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        Lingver.init(this)
    }
}