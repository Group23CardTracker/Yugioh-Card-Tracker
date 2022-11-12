package com.example.yu_gi_ohcardtracker.bannedandNew

import android.content.Context
import android.content.Intent
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

class NewCardAdapter(private val context: Context, private val newCards: List<DisplayCard>) :
    RecyclerView.Adapter<NewCardAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newCard = newCards[position]
        holder.bind(newCard)
    }

    // creating a variable for array list and context.
    private var cardModelArrayList: ArrayList<DisplayCard>? = null

    // creating a constructor for our variables.
    fun CardAdapter(theCards: ArrayList<DisplayCard>, context: Context?) {
        this.cardModelArrayList = theCards
    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<DisplayCard>) {
        // below line is to add our filtered
        // list in our card array list.
        cardModelArrayList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
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

        fun bind(newCard: DisplayCard){
            newCardTextView.text = newCard.name
            setNameTextView.text = "Set: " + newCard.setName
            setRarityTextView.text = "Rarity: " + newCard.setRarity

            Glide.with(context).load(newCard.imageUrl).into(newCardImageView)
        }

        override fun onClick(v: View?){
            val newcard = newCards[absoluteAdapterPosition]
            val intent = Intent(context, CardDetailActivity::class.java)
            intent.putExtra("theCard", newcard)
            // For the search
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
