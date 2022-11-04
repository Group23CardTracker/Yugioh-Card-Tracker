package com.example.yu_gi_ohcardtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewCardAdapter(private val context: Context, private val newCards: List<NewCard>) :
    RecyclerView.Adapter<NewCardAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newCard = newCards[position]
        holder.bind(newCard)
    }

    override fun getItemCount() = newCards.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{

        private val newCardTextView = itemView.findViewById<TextView>(R.id.new_card_name)
        private val newCardImageView = itemView.findViewById<ImageView>(R.id.new_card_image)
        private val setNameTextView = itemView.findViewById<TextView>(R.id.set_name)
        private val setRarityTextView = itemView.findViewById<TextView>(R.id.set_rarity)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(newCard: NewCard){
            newCardTextView.text = newCard.name
            setNameTextView.text = "Set: " + newCard.setName
            setRarityTextView.text = "Rarity: " + newCard.setRarity

            Glide.with(context).load(newCard.imageUrl).into(newCardImageView)
        }

        override fun onClick(v: View?){

        }
    }
}
