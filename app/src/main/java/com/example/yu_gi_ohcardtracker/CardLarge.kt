package com.example.yu_gi_ohcardtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class CardLarge : AppCompatActivity() {


    private lateinit var cardImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_large)

        cardImage = findViewById(R.id.cardImage)
        val currentCard = intent.getSerializableExtra("theCard")
        val cardName = intent.getSerializableExtra("theCardName")
        // Setting the label at the top menu to the card name
        this.setTitle(cardName.toString())
        Glide.with(this@CardLarge)
            .load(currentCard)
            .into(cardImage)
    }


}