package com.example.yu_gi_ohcardtracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class Home : Fragment() {

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
        val context = view.context
        itemsRecyclerView = view.findViewById(R.id.itemsRv)

        val itemAdapter = ItemAdapter(context, cards)
        itemsRecyclerView.adapter = itemAdapter

        itemsRecyclerView.layoutManager = LinearLayoutManager(context).also {
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            itemsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            (activity?.application as MyApplication).db.cardDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Card(
                        entity.name,
                        entity.img,
                    )
                }.also { mappedList ->
                    cards.clear()
                    cards.addAll(mappedList)
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }

        return view
    }

    companion object {
        fun newInstance(): Home {
            return Home()
        }
    }
}