package com.ns.quotesapp.ui.fragments.saved_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.quotesapp.R
import com.ns.quotesapp.data.model.Quote
import com.ns.quotesapp.databinding.FragmentSavedBinding
import com.ns.quotesapp.ui.adapters.SavedAdapter
import com.ns.quotesapp.ui.fragments.quote_fragment.QuoteViewModel
import com.ns.quotesapp.util.DeleteClick


class SavedFragment : Fragment(R.layout.fragment_saved), DeleteClick {

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

        binding.toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_savedFragment2_to_quoteFragment)
        }
    }

    private fun setupRecyclerView() {
        savedAdapter = SavedAdapter(this)
        binding.recyclerView.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDelete(quote: Quote) {
        viewModel.deleteQuote(quote)
        Toast.makeText(view?.context, "Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}