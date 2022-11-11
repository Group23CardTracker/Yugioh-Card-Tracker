package com.example.yu_gi_ohcardtracker

interface HomeInteractionListener {
    abstract val menuInflater: Any

    fun onItemClick(item: DisplayCard)
}