package com.example.yu_gi_ohcardtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
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

        // Get the name from the intent and make new webrequest with it.
        val currentCard = intent.getSerializableExtra("theCard")

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["name"] = currentCard.toString()
        client[
                "https://db.ygoprodeck.com/api/v7/cardinfo.php",
                params,
                object : JsonHttpResponseHandler()
                {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        val resultsJSON : JSONArray = json.jsonObject.getJSONArray("data")
                        val gson = Gson()
                        val arrayCards = object : TypeToken<List<CardDetail>>() {}.type
                        val models : List<CardDetail> = gson.fromJson(resultsJSON.toString(), arrayCards)

                        cardName.text = models[0].name
                        cardDesc.text = models[0].desc
                        cardLevel.text = "Level " + models[0].level.toString()
                        cardAtk.text = "Atk: " + models[0].atk.toString()
                        cardDef.text = "Def: " + models[0].def.toString()
                        cardPrice.text = "Price: " + models[0].prices?.get(0)?.cardmarket_price.toString()
                        Glide.with(this@CardDetailActivity)
                            .load(models[0].images?.get(0)?.imageUrl.toString())
                            .into(cardImage)
                        imgUrl = models[0].images?.get(0)?.imageUrl.toString()
                        // cardPrice.text = "Price: " + cardDeats.price?.diffPrice?.cMPrice.toString()
                        // Look for this in Logcat:
                        Log.d("Response", resultsJSON.toString())
                        Log.d("Response", models[0].prices?.get(0).toString())
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("Response", errorResponse)
                        }
                    }
                }]

        findViewById<ImageView>(R.id.cardImage).setOnClickListener{
            // If there is an image url then open a new activity to show large picture.
            if (imgUrl != null) {
                val cardLargeIntent = Intent(this, CardLarge::class.java)
                cardLargeIntent.putExtra("theCard", imgUrl)
                this?.startActivity(cardLargeIntent)
            }
        }


    }

}