package com.example.yu_gi_ohcardtracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Card>,
                  private val mListener: HomeInteractionListener?
    ) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val cardName = itemView.findViewById<TextView>(R.id.cardNameTv)
        private val cardImage = itemView.findViewById<TextView>(R.id.cardImage)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(curItem: Card) {
            cardName.text = curItem.name
        }

        override fun onClick(v: View?) {

        }

    }
}