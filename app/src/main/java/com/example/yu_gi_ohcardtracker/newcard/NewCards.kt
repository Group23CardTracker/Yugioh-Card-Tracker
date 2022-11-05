package com.example.yu_gi_ohcardtracker.newcard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.yu_gi_ohcardtracker.R
import com.example.yu_gi_ohcardtracker.YugiohApplication
import com.example.yu_gi_ohcardtracker.createJson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "NewCardFragment"

class NewCards : Fragment() {

    private val newCards = mutableListOf<DisplayNewCard>()
    private lateinit var newCardAdapter: NewCardAdapter
    private lateinit var newCardsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_new_cards, container, false)

        newCardsRecyclerView = view.findViewById(R.id.newcard_recycler_view)
        newCardsRecyclerView.layoutManager = LinearLayoutManager(context).also{
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            newCardsRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        newCardsRecyclerView.setHasFixedSize(true)
        newCardAdapter = NewCardAdapter(view.context, newCards)
        newCardsRecyclerView.adapter = newCardAdapter

        lifecycleScope.launch{
            (requireActivity().application as YugiohApplication).newcarddb.newCardDao().getAll().collect{ databaseList ->
                databaseList.map {entity ->
                    DisplayNewCard(
                        entity.name,
                        entity.setName,
                        entity.setRarity,
                        entity.imageUrl,
                    )
                }.also{mappedList ->
                    newCards.clear()
                    newCards.addAll(mappedList)
                    newCardAdapter.notifyDataSetChanged()
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCards()
    }

    companion object {
        fun newInstance(): NewCards {
            return NewCards()
        }
    }

    private fun fetchCards(){
        val c = Calendar.getInstance()
        var d = c.time
        val df = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val endDate = df.format(d)
        c.add(Calendar.DATE, -60)
        d = c.time
        val startDate = df.format(d)
        println("https://db.ygoprodeck.com/api/v7/cardinfo.php?&startdate=$startDate&enddate=$endDate&dataregion=tcg_date")
        val client = AsyncHttpClient()
        client.get("https://db.ygoprodeck.com/api/v7/cardinfo.php?&startdate=$startDate&enddate=$endDate&dataregion=tcg_date", object : JsonHttpResponseHandler(){
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
                        SearchNewCardData.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.data?.let{list ->
                        lifecycleScope.launch(IO) {
                            (requireActivity().application as YugiohApplication).newcarddb.newCardDao()
                                .deleteAll()
                            (requireActivity().application as YugiohApplication).newcarddb.newCardDao()
                                .insertAll(list.map {
                                    NewCardEntity(
                                        name = it.name,
                                        setName = it.setName,
                                        setRarity = it.setRarity,
                                        imageUrl = it.imageUrl,
                                    )
                                })
                        }
                    }
                    newCardAdapter.notifyDataSetChanged()
                } catch(e: JSONException){
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}