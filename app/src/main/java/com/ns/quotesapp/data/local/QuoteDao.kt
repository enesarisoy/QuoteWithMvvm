package com.ns.quotesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ns.quotesapp.data.model.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(quote: Quote): Long

    @Query("SELECT * FROM quote")
    fun getSavedQuotes(): LiveData<List<Quote>>

    @Delete
    suspend fun deleteQuote(quote: Quote)
}