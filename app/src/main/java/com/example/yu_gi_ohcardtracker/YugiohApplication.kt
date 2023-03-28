package com.example.yu_gi_ohcardtracker

import android.app.Application
import com.example.yu_gi_ohcardtracker.bannedandNew.NewCardAppDatabase
import com.example.yu_gi_ohcardtracker.collection.CollectionAppDatabase

class YugiohApplication: Application(){
    val newcarddb by lazy { NewCardAppDatabase.getInstance(this)}
    val db by lazy { AppDatabase.getInstance(this) }
    val collectiondb by lazy {CollectionAppDatabase.getInstance(this)}
}