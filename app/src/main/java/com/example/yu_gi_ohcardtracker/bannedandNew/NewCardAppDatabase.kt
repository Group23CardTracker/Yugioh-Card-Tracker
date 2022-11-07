package com.example.yu_gi_ohcardtracker.bannedandNew

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewCardEntity::class], version = 1)
abstract class NewCardAppDatabase : RoomDatabase(){
    abstract fun newCardDao(): NewCardDao

    companion object{
        @Volatile
        private var INSTANCE: NewCardAppDatabase? = null

        fun getInstance(context: Context): NewCardAppDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{ INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewCardAppDatabase::class.java, "NewCard-db"
            ).build()
    }
}