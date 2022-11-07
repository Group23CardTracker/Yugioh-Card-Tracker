package com.example.yu_gi_ohcardtracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

import android.widget.Toast
import java.util.*



class HomeFragment : Fragment(), HomeInteractionListener {

    private val cards = mutableListOf<Card>()
    private lateinit var itemsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.itemsRv) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        updateAdapter(progressBar, recyclerView)





//        val itemAdapter = ItemAdapter(cards, context)
//        itemsRecyclerView.adapter = itemAdapter
//
//        itemsRecyclerView.layoutManager = LinearLayoutManager(context).also {
//            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
//            itemsRecyclerView.addItemDecoration(dividerItemDecoration)
//        }
// DAO
//        lifecycleScope.launch {
//            (activity?.application as YugiohApplication).db.cardDao().getAll().collect { databaseList ->
//                databaseList.map { entity ->
//                    Card(
//                        entity.name,
//                        entity.img,
//                    )
//                }.also { mappedList ->
//                    cards.clear()
//                    cards.addAll(mappedList)
//                    itemAdapter.notifyDataSetChanged()
//                }
//            }
//        }

        return view
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        client[
                "https://db.ygoprodeck.com/api/v7/cardinfo.php",
                object : JsonHttpResponseHandler()
                {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        progressBar.hide()
                        val resultsJSON : JSONArray = json.jsonObject.getJSONArray("data")

                        val gson = Gson()
                        val arrayCardType = object : TypeToken<List<Card>>() {}.type

                        val models : List<Card> = gson.fromJson(resultsJSON.toString(), arrayCardType)
                        recyclerView.adapter = ItemAdapter(models, this@HomeFragment)


                        // Look for this in Logcat:
                        Log.d("Response", resultsJSON.toString())
                        Log.d("Response", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("Response", errorResponse)
                        }
                    }
                }]


    }

    override fun onItemClick(item: Card) {
        val name = item.name

        Toast.makeText(context, "test: " + item.name, Toast.LENGTH_SHORT).show()
        val cardDetailIntent = Intent(context, CardDetailActivity::class.java)
        cardDetailIntent.putExtra("theCard", item)
        context?.startActivity(cardDetailIntent)
    }

    // calling on create option menu
    // layout to inflate our menu file.
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        // below line is to get our inflater
//
//        // inside inflater we are inflating our menu file.
//        inflater.inflate(R.menu.search_menu, menu)
//
//        // below line is to get our menu item.
//        val searchItem = menu.findItem(R.id.actionSearch)
//
//        // getting search view of our item.
//        val searchView = searchItem.actionView
//
//        // below line is to call set on query text listener method.
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
//                filter(newText)
//                return false
//            }
//        })
//        return true
//    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<Card>()

        // running a for loop to compare elements.
        for (item in cards) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name?.lowercase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
//            ItemAdapter.filterList(filteredlist)
        }
    }
}