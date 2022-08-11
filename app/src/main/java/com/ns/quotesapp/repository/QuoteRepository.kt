package com.ns.quotesapp.repository

import com.ns.quotesapp.data.local.QuoteDao
import com.ns.quotesapp.data.model.Quote
import com.ns.quotesapp.data.remote.RetrofitInstance

class QuoteRepository(
    private val dao: QuoteDao
) {

    suspend fun getAllQuotes() = RetrofitInstance.api.getQuotes()

    suspend fun upsert(quote: Quote) = dao.upsert(quote)
    suspend fun delete(quote: Quote) = dao.deleteQuote(quote)
    fun checkExists(id: Int) = dao.checkExist(id)
    fun getSavedQuotes() = dao.getSavedQuotes()
}