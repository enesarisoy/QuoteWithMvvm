package com.ns.quotesapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable {
}