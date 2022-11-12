package com.example.yu_gi_ohcardtracker.collection

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CollectionEntity::class], version = 1)
abstract class CollectionAppDatabase : RoomDatabase(){
    abstract fun collectionDao(): CollectionDao

    companion object{
        @Volatile
        private var INSTANCE: CollectionAppDatabase? = null

        fun getInstance(context: Context): CollectionAppDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{ INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CollectionAppDatabase::class.java, "collection-db"
            ).build()
    }
}