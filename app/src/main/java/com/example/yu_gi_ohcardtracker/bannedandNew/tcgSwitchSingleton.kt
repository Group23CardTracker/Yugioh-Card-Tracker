package com.example.yu_gi_ohcardtracker.bannedandNew

object tcgSwitchSingletion{
    var tcgOn = true

    fun toggleState(){
        tcgOn = !tcgOn
    }
}