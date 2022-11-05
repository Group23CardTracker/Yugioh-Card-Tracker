package com.example.yu_gi_ohcardtracker

import android.app.Application


class MyApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}
