package com.ns.quotesapp.util

import com.ns.quotesapp.data.model.Quote

interface PopupClick {
    fun onClickPopup(quote: Quote)
}