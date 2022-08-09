package com.ns.quotesapp.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "quote",
    primaryKeys = ["id", "quote"]
)
data class Quote(
    var id: Int,

    @SerializedName("q")
    val quote: String,
    @SerializedName("a")
    val author: String
) {
}