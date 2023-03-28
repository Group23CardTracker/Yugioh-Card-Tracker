package com.example.yu_gi_ohcardtracker.bannedandNew

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yu_gi_ohcardtracker.*
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

private const val TAG = "BannedListFragment"

class BanList: Fragment(), View.OnClickListener {

    private val bannedCards = ArrayList<DisplayCard>()
    private lateinit var bannedCardRecyclerView: RecyclerView
    private lateinit var bannedCardAdapter: BannedAdapter
    private lateinit var banListToggle: AppCompatToggleButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // Setting the label at the top menu to "banned cards"
        activity?.title = "Banned Cards"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ban_list, container, false)
        
        bannedCardRecyclerView = view.findViewById(R.id.banlist_recycler_view)
        bannedCardRecyclerView.layoutManager = LinearLayoutManager(context).also{
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            bannedCardRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        bannedCardRecyclerView.setHasFixedSize(true)
        bannedCardAdapter = BannedAdapter(view.context, bannedCards)
        bannedCardRecyclerView.adapter = bannedCardAdapter
        banListToggle = view.findViewById(R.id.banlistToggleButton)


        if(!tcgSwitchSingletion.tcgOn){
            getOCGCards()
        }
        else{
            getTCGCards()
        }
        banListToggle.setOnClickListener(this)
        return view
    }

    private fun getTCGCards(){
        lifecycleScope.launch{
            (requireActivity().application as YugiohApplication).db.cardDao().getAllBannedTCG("").collect{ databaseList ->
                databaseList.map {entity ->
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
                        entity.tcgBanStatus,
                        entity.ocgBanStatus,

                        entity.setName,
                        entity.setRarity
                    )
                }.also{mappedList ->
                    bannedCards.clear()
                    bannedCards.addAll(mappedList)
                    bannedCardAdapter.setData(mappedList)
                }
            }
        }
    }

    private fun getOCGCards(){
        lifecycleScope.launch{
            (requireActivity().application as YugiohApplication).db.cardDao().getAllBannedOCG("").collect{ databaseList ->
                databaseList.map {entity ->
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
                        entity.tcgBanStatus,
                        entity.ocgBanStatus,
                        entity.setName,
                        entity.setRarity
                    )
                }.also{mappedList ->
                    bannedCards.clear()
                    bannedCards.addAll(mappedList)
                    bannedCardAdapter.setData(mappedList)
                }
            }
        }
    }

    // For the search
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate menu with items using MenuInflator
        //val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        // Initialise menu item search bar
        // with id and take its object
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
        android.widget.SearchView.OnQueryTextListener{
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
        bannedCards.forEach{
            // checking if the entered string matched with any item of our recycler view.
            if (it.name?.lowercase()!!.contains(text.lowercase())) {
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
            bannedCardAdapter.filterList(filteredlist)
        }
    }

    override fun onClick(p0: View?) {
        if(banListToggle.isChecked){
            getOCGCards()
            Log.i(TAG, "Switching")
        }
        else{
            getTCGCards()
        }
        tcgSwitchSingletion.toggleState()
    }


}