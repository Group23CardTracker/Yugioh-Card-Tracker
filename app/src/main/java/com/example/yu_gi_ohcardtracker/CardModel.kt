package com.example.yu_gi_ohcardtracker

class CardModel (private var cardName: String) {
    // creating getter and setter methods.
    fun getCourseName(): String {
        return cardName
    }

    fun setCourseName(courseName: String) {
        this.cardName = cardName
    }
}