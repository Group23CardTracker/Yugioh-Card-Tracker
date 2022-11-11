package com.example.yu_gi_ohcardtracker.collection

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yu_gi_ohcardtracker.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "CollectionFragment"

class CollectionFragment(override val menuInflater: Any) : Fragment(), HomeInteractionListener {

    private val collectionCards = mutableListOf<DisplayCard>()
    private lateinit var collectionCardRecyclerView: RecyclerView
    private lateinit var collectionCardAdapter: CollectionAdapter
    private lateinit var valueTextView: TextView

    // For the search
    private var cards2 = arrayListOf<DisplayCard>()
    private lateinit var rec : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        activity?.setTitle("Favorites")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        collectionCardRecyclerView = view.findViewById(R.id.collection_recycler_view)
        collectionCardRecyclerView.layoutManager = LinearLayoutManager(context).also{
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            collectionCardRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        collectionCardRecyclerView.setHasFixedSize(true)
        collectionCardAdapter = CollectionAdapter(view.context, collectionCards)
        collectionCardRecyclerView.adapter = collectionCardAdapter
        valueTextView = view.findViewById(R.id.price_tag)

        rec = collectionCardRecyclerView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCards()
    }

    private fun fetchCards(){
        lifecycleScope.launch{
            (requireActivity().application as YugiohApplication).collectiondb.collectionDao().getAll().collect{databaseList ->
                databaseList.map { entity ->
                    DisplayCard(
                        entity.name,
                        entity.img,
                        entity.desc,
                        entity.level,
                        entity.atk,
                        entity.def,
                        entity.cardmarket_price,
                        entity.tcgPlayerPrice,
                        entity.ebayPrice,
                        entity.banStatus,
                        entity.setName,
                        entity.setRarity
                    )
                }.also{mappedList ->
                    collectionCards.clear()
                    collectionCards.addAll(mappedList)
                    cards2 = mappedList as ArrayList<DisplayCard>
                    var total = 0.00
                    for(card in collectionCards){
                        var price = card.cardmarket_price?.toDoubleOrNull()
                        if(price != null) total += price
                    }
                    valueTextView.text = "$$total"
                    collectionCardAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate menu with items using MenuInflator
        //val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        // Initialise menu item search bar
        // with id and take its object
        val searchViewItem = menu.findItem(R.id.actionSearch)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Override onQueryTextSubmit method which is call when submit query is searched
            override fun onQueryTextSubmit(query: String): Boolean {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                //filter(query)

                return false
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            override fun onQueryTextChange(newText: String): Boolean {

                filter(newText)
                return false
            }
        })
        return
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<DisplayCard>()
        // running a for loop to compare elements
        cards2.forEach{
            // checking if the entered string matched with any item of our recycler view.
            if (it.name?.lowercase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(it)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            rec.adapter = context?.let { CollectionAdapter(it.applicationContext, filteredlist) }
        }
    }


    override fun onItemClick(item: DisplayCard) {
        TODO("Not yet implemented")
    }
}