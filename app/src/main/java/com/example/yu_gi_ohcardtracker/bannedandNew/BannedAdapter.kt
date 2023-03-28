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
import com.example.yu_gi_ohcardtracker.CardDetailActivity
import com.example.yu_gi_ohcardtracker.DisplayCard
import com.example.yu_gi_ohcardtracker.R

class BannedAdapter(private val context: Context, private var banned: ArrayList<DisplayCard>) :
    RecyclerView.Adapter<BannedAdapter.ViewHolder>(){

    fun setData(data: List<DisplayCard>){
        banned.run{
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bannedcard = banned[position]
        holder.bind(bannedcard)
    }

    fun filterList(filterlist: ArrayList<DisplayCard>){
        banned = filterlist
        notifyDataSetChanged()
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

        fun bind(bannedCard: DisplayCard){
            bannedCardTextView.text = bannedCard.name
            val banText = if(tcgSwitchSingletion.tcgOn){
                bannedCard.tcgBanStatus
            } else{
                bannedCard.ocgBanStatus
            }
            bannedCardStatusTextView.text = banText
            //Change the text color for status
            when (banText) {
                "Banned" -> bannedCardStatusTextView.setTextColor(Color.RED)
                "Limited" -> bannedCardStatusTextView.setTextColor(Color.parseColor("#FFA500"))
                else -> bannedCardStatusTextView.setTextColor(Color.YELLOW)
            }

            Glide.with(context).load(bannedCard.imageUrl).into(bannedCardImageView)
        }

        override fun onClick(v: View?){
            val bannedCard = banned[absoluteAdapterPosition]
            val intent = Intent(context, CardDetailActivity::class.java)
            intent.putExtra("theCard", bannedCard)
            // For the search
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    }
