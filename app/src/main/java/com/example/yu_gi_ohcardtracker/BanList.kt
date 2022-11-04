package com.example.yu_gi_ohcardtracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "BannedListFragment"

class BanList : Fragment() {

    private val bannedCards = mutableListOf<BannedCard>()
    private lateinit var bannedCardRecyclerView: RecyclerView
    private lateinit var bannedCardAdapter: BannedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ban_list, container, false)

        val layoutManager = LinearLayoutManager(context)
        bannedCardRecyclerView = view.findViewById(R.id.banlist_recycler_view)
        bannedCardRecyclerView.layoutManager = layoutManager
        bannedCardRecyclerView.setHasFixedSize(true)
        bannedCardAdapter = BannedAdapter(view.context, bannedCards)
        bannedCardRecyclerView.adapter = bannedCardAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCards()
    }

    private fun fetchCards(){
        val client = AsyncHttpClient()
        client.get("https://db.ygoprodeck.com/api/v7/cardinfo.php?banlist=tcg", object : JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch Cards: $statusCode")
            }
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON){
                Log.i(TAG, "Successfully fetched Cards: $json")
                try{
                    val parsedJson = createJson().decodeFromString(
                        SearchNewData.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.data?.let{list ->
                        bannedCards.addAll(list)
                    }
                    bannedCardAdapter.notifyDataSetChanged()
                } catch(e: JSONException){
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }

    companion object {
        fun newInstance(): BanList{
            return BanList()
        }
    }
}