package com.example.yu_gi_ohcardtracker

import android.content.Context
import android.content.Intent
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAdapter(private val items: MutableList<DisplayCard>,
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

        holder.itemView.setOnClickListener {
            holder.itemView?.let { _ ->
                mListener?.onItemClick(item)
            }
        }
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

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val cardName = itemView.findViewById<TextView>(R.id.cardNameTv)
        private val cardImage = itemView.findViewById<ImageView>(R.id.cardImage)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(curItem: DisplayCard) {
            cardName.text = curItem.name
            Glide.with(itemView)
                .load(curItem.imageUrl)
                .into(cardImage)
        }

        override fun onClick(v: View?) {

        }

    }
}