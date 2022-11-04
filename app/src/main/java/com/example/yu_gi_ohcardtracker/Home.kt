package com.example.yu_gi_ohcardtracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONArray



class Home : Fragment(), HomeInteractionListener {

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
//            (activity?.application as MyApplication).db.cardDao().getAll().collect { databaseList ->
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
        fun newInstance(): Home {
            return Home()
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
                        Log.d("MoviesFragment", "HERE")
                        val resultsJSON : JSONArray = json.jsonObject.getJSONArray("data")

                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<Card>>() {}.type

                        val models : List<Card> = gson.fromJson(resultsJSON.toString(), arrayMovieType)
                        recyclerView.adapter = ItemAdapter(models, this@Home)

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
        TODO("Not yet implemented")
    }
}