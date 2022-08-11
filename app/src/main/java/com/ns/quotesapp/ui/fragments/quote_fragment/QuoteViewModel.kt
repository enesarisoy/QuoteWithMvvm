package com.ns.quotesapp.ui.fragments.quote_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ns.quotesapp.data.local.QuoteDatabase
import com.ns.quotesapp.data.model.Quote
import com.ns.quotesapp.repository.QuoteRepository
import com.ns.quotesapp.util.CheckInternet
import com.ns.quotesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class QuoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuoteRepository
    private val internet: CheckInternet
    val quote: MutableLiveData<Resource<Quote>> = MutableLiveData()
    val saved: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val dao = QuoteDatabase.invoke(application).quoteDao()
        internet = CheckInternet(application)
        repository = QuoteRepository(dao)
        getAllQuotes()
    }

    fun getAllQuotes() = viewModelScope.launch {
        saved.postValue(false)
        quote.postValue(Resource.Loading())
        val response = repository.getAllQuotes()
        quote.postValue(handleQuoteResponse(response))
    }

    fun saveQuote(quote: Quote) = viewModelScope.launch {
        saved.postValue(true)
        repository.upsert(quote)
    }

    fun getSavedQuotes() = repository.getSavedQuotes()

    fun deleteQuote(quote: Quote) = viewModelScope.launch {
        repository.delete(quote)
        saved.postValue(false)
    }

    private fun handleQuoteResponse(response: Response<List<Quote>>): Resource<Quote> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}