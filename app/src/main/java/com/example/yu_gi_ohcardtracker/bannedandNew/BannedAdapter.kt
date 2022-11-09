package com.example.yu_gi_ohcardtracker.bannedandNew

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yu_gi_ohcardtracker.R

class BannedAdapter(private val context: Context, private val banned: List<DisplaySCard>) :
    RecyclerView.Adapter<BannedAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bannedcard = banned[position]
        holder.bind(bannedcard)
    }

    override fun getItemCount() = banned.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        private val bannedCardTextView = itemView.findViewById<TextView>(R.id.card_banned_name)
        private val bannedCardImageView = itemView.findViewById<ImageView>(R.id.card_banned_image)
        private val bannedCardStatusTextView = itemView.findViewById<TextView>(R.id.card_banned_status)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(bannedCard: DisplaySCard){
            bannedCardTextView.text = bannedCard.name
            val bantext = bannedCard.banStatus
            bannedCardStatusTextView.text = bantext
            //Change the text color for status
            if(bantext == "Banned") bannedCardStatusTextView.setTextColor(Color.RED)
            else if(bantext == "Limited") bannedCardStatusTextView.setTextColor(Color.parseColor("#FFA500"))
            else bannedCardStatusTextView.setTextColor(Color.YELLOW)

            Glide.with(context).load(bannedCard.imageUrl).into(bannedCardImageView)
        }

        override fun onClick(v: View?){
            val bannedCard = banned[absoluteAdapterPosition]
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("CardName", bannedCard.name)
            intent.putExtra("CardImage", bannedCard.imageUrl)
            // For the search
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
    }
