package com.ns.quotesapp.ui.fragments.quote_fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.ns.quotesapp.R
import com.ns.quotesapp.data.model.Quote
import com.ns.quotesapp.databinding.FragmentQuoteBinding
import com.ns.quotesapp.ui.adapters.QuoteAdapter
import com.ns.quotesapp.util.Resource


class QuoteFragment : Fragment(R.layout.fragment_quote) {

    private var _binding: FragmentQuoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var quoteAdapter: QuoteAdapter
    private var quoteShown = false
    private var isLiked = false
    private val TAG = "QuoteFragment"

    private val viewModel: QuoteViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuoteBinding.bind(view)
        setupRecyclerView()

        viewModel.quote.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                    quoteShown = false
                }
                is Resource.Success -> {
                    hideProgressBar()
                    quoteShown = true
                    response.data?.let { quoteResponse ->
                        quoteAdapter.differ.submitList(quoteResponse)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    quoteShown = false
                    Log.e(TAG, response.message.toString())
                }

            }
        })

        binding.toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_quoteFragment_to_savedFragment2)
        }
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        quoteAdapter = QuoteAdapter()
        binding.recyclerView.apply {
            adapter = quoteAdapter
            layoutManager =
                LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)
            setSnapHelper(layoutManager as LinearLayoutManager)

        }
    }

    private fun setSnapHelper(layoutManager: LinearLayoutManager) {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)



        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                 if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                     val centerView = snapHelper.findSnapView(layoutManager)
                     val pos: Int = (layoutManager).getPosition(centerView!!)

                     val quote = quoteAdapter.differ.currentList[pos]
                     Log.e(TAG, "Pos: $pos" )

                     fabOnClick(quote)
                 }
            }
        })
    }

    private fun fabOnClick(quote: Quote) {
        viewModel.saved.observe(viewLifecycleOwner) {
            isLiked = it
            binding.fab.setOnClickListener {
                if (isLiked) {

                    viewModel.deleteQuote(quote)
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(context, "Already exists", Toast.LENGTH_SHORT).show()

                } else {

                    viewModel.saveQuote(quote)
                    isLiked = true
                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}