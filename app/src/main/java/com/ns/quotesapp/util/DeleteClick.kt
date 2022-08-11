package com.ns.quotesapp.util

import com.ns.quotesapp.data.model.Quote

interface DeleteClick {
    fun onDelete(quote: Quote)
}