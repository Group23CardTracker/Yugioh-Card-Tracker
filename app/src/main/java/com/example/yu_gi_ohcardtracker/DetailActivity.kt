package com.example.yu_gi_ohcardtracker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity(){

    private lateinit var mediaImageView: ImageView
    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mediaImageView = findViewById(R.id.card_image)
        nameTextView = findViewById(R.id.card_name)

        val name = intent.getSerializableExtra("CardName") as String
        val imageUrl = intent.getSerializableExtra("CardImage") as String

        nameTextView.text = name

        Glide.with(this).load(imageUrl).into(mediaImageView)
        
    }
}