package com.ns.quotesapp.data.remote

import com.ns.quotesapp.data.model.Quote
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {

    @GET("quotes")
    suspend fun getQuotes(): Response<List<Quote>>
}