package com.ns.quotesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.quotesapp.data.model.Quote
import com.ns.quotesapp.databinding.SavedRowBinding
import com.ns.quotesapp.util.DeleteClick

class SavedAdapter(
    private val deleteClick: DeleteClick
) : RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {
    inner class SavedViewHolder(val binding: SavedRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.quote == newItem.quote
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        return SavedViewHolder(
            SavedRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val quote = differ.currentList[position]

        holder.binding.run {
            tvSavedQuote.text = quote.quote
            tvSavedAuth.text = quote.author
            ivVert.setOnClickListener {
                deleteClick.onDelete(quote)
            }
        }
    }


    override fun getItemCount(): Int = differ.currentList.size
}