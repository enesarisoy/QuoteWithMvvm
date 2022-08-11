package com.ns.quotesapp.ui.fragments.saved_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.quotesapp.R
import com.ns.quotesapp.data.model.Quote
import com.ns.quotesapp.databinding.FragmentSavedBinding
import com.ns.quotesapp.ui.adapters.SavedAdapter
import com.ns.quotesapp.ui.fragments.quote_fragment.QuoteViewModel
import com.ns.quotesapp.util.PopupClick
import com.ns.quotesapp.util.Resource


class SavedFragment : Fragment(R.layout.fragment_saved), PopupClick {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuoteViewModel by activityViewModels()
    private lateinit var savedAdapter: SavedAdapter
    private val TAG = "SavedFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)
        setupRecyclerView()

        viewModel.getSavedQuotes().observe(viewLifecycleOwner) {
            savedAdapter.differ.submitList(it.toList())
        }
    }

    private fun setupRecyclerView() {
        savedAdapter = SavedAdapter(this)
        binding.recyclerView.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickPopup(quote: Quote) {
        viewModel.deleteQuote(quote)
        Toast.makeText(view?.context, "Deleted", Toast.LENGTH_SHORT).show()
    }
}