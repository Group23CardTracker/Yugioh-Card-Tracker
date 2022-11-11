package com.example.yu_gi_ohcardtracker.bannedandNew

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.yu_gi_ohcardtracker.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException
import java.util.*

private const val TAG = "BannedListFragment"

class BanList(override val menuInflater: Any) : Fragment(), HomeInteractionListener {

    private val bannedCards = mutableListOf<DisplayCard>()
    private lateinit var bannedCardRecyclerView: RecyclerView
    private lateinit var bannedCardAdapter: BannedAdapter

    // For the search
    private var cards2 = arrayListOf<DisplayCard>()
    private lateinit var rec : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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


        // Set the rec for search to newcards
        rec = bannedCardRecyclerView

        //get data from database
        lifecycleScope.launch{
            (requireActivity().application as YugiohApplication).bandb.bannedDao().getAll().collect{ databaseList ->
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
                        entity.banStatus,
                        entity.setName,
                        entity.setRarity
                    )
                }.also{mappedList ->
                    bannedCards.clear()
                    bannedCards.addAll(mappedList)
                    // for the search
                    cards2 = mappedList as ArrayList<DisplayCard>
                    bannedCardAdapter.notifyDataSetChanged()
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar

        fetchCards(progressBar)
    }

    private fun fetchCards(progressBar: ContentLoadingProgressBar){
        progressBar.show()
        val client = AsyncHttpClient()
        client.get("https://db.ygoprodeck.com/api/v7/cardinfo.php?banlist=tcg", object : JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                progressBar.hide()
                Log.e(TAG, "Failed to fetch Cards: $statusCode")
            }
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON){
                Log.i(TAG, "Successfully fetched Cards: $json")
                progressBar.hide()
                try{
                    val parsedJson = createJson().decodeFromString(
                        SearchData.serializer(),
                        json.jsonObject.toString()
                    )
                    if(!isAdded){
                        return
                    }
                    parsedJson.data?.let{list ->
                        lifecycleScope.launch(IO) {
                            (requireActivity().application as YugiohApplication).bandb.bannedDao()
                                .deleteAll()
                            (requireActivity().application as YugiohApplication).bandb.bannedDao()
                                .insertAll(list.map {
                                    BannedEntity(
                                        name = it.name,
                                        img = it.imageUrl,
                                        desc = it.desc,
                                        level = it.level,
                                        atk = it.atk,
                                        def = it.def,
                                        cardmarket_price = it.cardmarketPrice,
                                        tcgPlayerPrice = it.tcgplayerPrice,
                                        ebayPrice = it.ebay,
                                        banStatus = it.banlistInfo?.banStatus,
                                        setName = it.setName,
                                        setRarity = it.setRarity
                                    )
                                })
                        }
                    }
                    bannedCardAdapter.notifyDataSetChanged()
                } catch(e: JSONException){
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }

    companion object {
//        fun newInstance(): BanList {
//            return BanList()
//        }
    }

    // For the search
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
            rec.adapter = context?.let { BannedAdapter(it.applicationContext, filteredlist) }
        }
    }
    

    override fun onItemClick(item: Card) {
        TODO("Not yet implemented")
    }


}