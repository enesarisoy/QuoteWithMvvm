package com.ns.quotesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ns.quotesapp.data.model.Quote

@Database(entities = [Quote::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile   //if instance changes, other threads will immediately see this change
        private var instance: QuoteDatabase? = null
        private val LOCK = Any()

        //whenever create an instance of database invoke fun gonna called
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                QuoteDatabase::class.java,
                "quote_db.db"
            ).build()
    }
}