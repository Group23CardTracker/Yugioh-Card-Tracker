package com.example.yu_gi_ohcardtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BannedAdapter(private val context: Context, private val banned: List<BannedCard>) :
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

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(bannedCard: BannedCard){
            bannedCardTextView.text = bannedCard.name

            Glide.with(context).load(bannedCard.imageUrl).into(bannedCardImageView)
        }

        override fun onClick(v: View?){
            
        }
    }
    }