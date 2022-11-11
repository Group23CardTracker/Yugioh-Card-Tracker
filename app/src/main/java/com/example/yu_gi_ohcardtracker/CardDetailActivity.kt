package com.example.yu_gi_ohcardtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

class CardDetailActivity : AppCompatActivity() {

    private lateinit var cardImage: ImageView
    private lateinit var cardName: TextView
    private lateinit var cardDesc: TextView
    private lateinit var cardLevel: TextView
    private lateinit var cardAtk: TextView
    private lateinit var cardDef: TextView
    private lateinit var cardPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        // Get views
        cardImage = findViewById(R.id.cardImage)
        cardName = findViewById(R.id.cardName)
        cardDesc = findViewById(R.id.cardDesc)
        cardLevel = findViewById(R.id.cardLevel)
        cardAtk = findViewById(R.id.cardAtk)
        cardDef = findViewById(R.id.cardDef)
        cardPrice = findViewById(R.id.cardPrice)


        var imgUrl = ""

        // Get the name from the intent and add it to the card
        val currentCard = intent.getSerializableExtra("theCard")
        if(currentCard is DisplayCard) {
            cardName.text = currentCard.name
            // Setting the label at the top menu to the card name
            this.setTitle(currentCard.name)
            cardDesc.text = currentCard.desc
            cardLevel.text = "Level: " + currentCard.level.toString()
            cardAtk.text = "Atk: " + currentCard.atk.toString()
            cardDef.text = "Def: " + currentCard.def.toString()
            cardPrice.text = "Price: $" + currentCard.cardmarket_price
            Glide.with(this)
                .load(currentCard.imageUrl)
                .into(cardImage)
            imgUrl = currentCard.imageUrl.toString()
            if(cardDef.text == "Def: null") {
                cardDef.isGone = true
            }

            if(cardAtk.text == "Atk: null") {
                cardAtk.isGone = true
            }

            if(cardLevel.text == "Level: null") {
                cardLevel.isGone = true
            }
            findViewById<ImageView>(R.id.cardImage).setOnClickListener {
                // If there is an image url then open a new activity to show large picture.
                if (imgUrl != null) {
                    val cardLargeIntent = Intent(this, CardLarge::class.java)
                    cardLargeIntent.putExtra("theCard", imgUrl)
                    cardLargeIntent.putExtra("theCardName", currentCard.name)
                    this?.startActivity(cardLargeIntent)
                }
            }
        }


    }

}