package com.example.yu_gi_ohcardtracker.collection

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

class CollectionAdapter(private val context: Context, private val collectionCards: List<DisplayCard>) :
    RecyclerView.Adapter<CollectionAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collectionCard = collectionCards[position]
        holder.bind(collectionCard)
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

    override fun getItemCount() = collectionCards.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{

        private val collectionCardTextView = itemView.findViewById<TextView>(R.id.collection_name)
        private val collectionCardImageView = itemView.findViewById<ImageView>(R.id.collection_image)
        private val valueTextView = itemView.findViewById<TextView>(R.id.collection_value)
        private val statusTextView = itemView.findViewById<TextView>(R.id.banStatus)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(collectionCard: DisplayCard){
            collectionCardTextView.text = collectionCard.name
            valueTextView.text = "$" + collectionCard.cardmarket_price
            val bantext = collectionCard.tcgBanStatus
            statusTextView.text = bantext
            //Change the text color for status
            if(bantext == "Banned") statusTextView.setTextColor(Color.RED)
            else if(bantext == "Limited") statusTextView.setTextColor(Color.parseColor("#FFA500"))
            else if(bantext == "Semi-Limited") statusTextView.setTextColor(Color.YELLOW)
            else {
                statusTextView.text = "Legal"
                statusTextView.setTextColor(Color.GREEN)
            }

            Glide.with(context).load(collectionCard.imageUrl).into(collectionCardImageView)
        }

        override fun onClick(v: View?){
            val collectionCard = collectionCards[absoluteAdapterPosition]
            val intent = Intent(context, CardDetailActivity::class.java)
            intent.putExtra("theCard", collectionCard)
            // For the search
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
