package com.example.yu_gi_ohcardtracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.yu_gi_ohcardtracker.bannedandNew.BanList
import com.example.yu_gi_ohcardtracker.bannedandNew.NewCards
import com.example.yu_gi_ohcardtracker.collection.CollectionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json
import com.example.yu_gi_ohcardtracker.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        
        setContentView(view)
        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val fragment1: Fragment = HomeFragment(menuInflater)
        val fragment2: Fragment = BanList(menuInflater)
        val fragment3: Fragment = NewCards(menuInflater)
        val fragment4: Fragment = CollectionFragment(menuInflater)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_home -> fragment = fragment1
                R.id.action_banlist -> fragment = fragment2
                R.id.action_newcards -> fragment = fragment3
                R.id.action_collection -> fragment = fragment4

            }
            fragmentManager.beginTransaction().replace(R.id.rlContainer, fragment).commit()
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_home




    }
}