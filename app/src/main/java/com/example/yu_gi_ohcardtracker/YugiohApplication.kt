package com.example.yu_gi_ohcardtracker

import android.app.Application
import com.example.yu_gi_ohcardtracker.banned.BannedAppDatabase
import com.example.yu_gi_ohcardtracker.newcard.NewCardAppDatabase

class YugiohApplication: Application(){
    val bandb by lazy { BannedAppDatabase.getInstance(this)}
    val newcarddb by lazy { NewCardAppDatabase.getInstance(this)}
    val db by lazy { AppDatabase.getInstance(this) }
}