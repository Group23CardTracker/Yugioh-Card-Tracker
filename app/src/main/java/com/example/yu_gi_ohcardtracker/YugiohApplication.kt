package com.example.yu_gi_ohcardtracker

import android.app.Application

class YugiohApplication: Application(){
    val bandb by lazy {BannedAppDatabase.getInstance(this)}
}