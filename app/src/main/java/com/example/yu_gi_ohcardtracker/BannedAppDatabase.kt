package com.example.yu_gi_ohcardtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BannedEntity::class], version = 1)
abstract class BannedAppDatabase : RoomDatabase(){
    abstract fun bannedDao(): BannedDao

    companion object{
        @Volatile
        private var INSTANCE: BannedAppDatabase? = null

        fun getInstance(context: Context): BannedAppDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{ INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BannedAppDatabase::class.java, "Banned-db"
            ).build()
    }
}