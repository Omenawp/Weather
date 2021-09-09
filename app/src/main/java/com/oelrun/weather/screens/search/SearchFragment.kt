package com.oelrun.weather.screens.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oelrun.weather.R
import com.oelrun.weather.adapters.SearchAdapter
import com.oelrun.weather.databinding.FragmentSearchBinding

class SearchFragment: Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val adapter = SearchAdapter {
            searchViewModel.onItemClicked(it)
        }
        binding.listCity.adapter = adapter

        searchViewModel.loading.observe(viewLifecycleOwner, {
            binding.loader.visibility = if(it) View.VISIBLE else View.GONE
        })

        searchViewModel.cityData.observe(viewLifecycleOwner, {
            if(it.isNullOrEmpty()) {
                val message = if(searchViewModel.search) {
                    resources.getText(R.string.search_error_message)
                } else {
                    resources.getText(R.string.search_no_data)
                }

                binding.errorText.text = message
                binding.errorText.visibility = View.VISIBLE
                binding.errorImage.visibility = View.VISIBLE
                binding.listCity.visibility = View.GONE
            } else {
                adapter.submitList(it)
                binding.errorText.visibility = View.GONE
                binding.errorImage.visibility = View.GONE
                binding.listCity.visibility = View.VISIBLE
            }
        })

        searchViewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let{
                Toast.makeText(this.requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })

        val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.search.setOnEditorActionListener {textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                val search = textView.text.toString()
                searchViewModel.citySearch(search)
                textView.clearFocus()
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
            }
            true
        }

        searchViewModel.closeSearch.observe(viewLifecycleOwner, {
            if(it) {
                binding.searchImage.performClick()
                searchViewModel.searchIsClosed()
            }
        })

        binding.searchImage.setOnClickListener {
            if(searchViewModel.search) {
                imm.hideSoftInputFromWindow(binding.search.windowToken, 0)
                binding.search.setText("")
                binding.search.clearFocus()
                searchViewModel.stopSearch()
                binding.search.visibility = View.INVISIBLE
                binding.searchImage.setImageDrawable(resources.getDrawable(R.drawable.ic_search))
            } else {
                binding.search.visibility = View.VISIBLE
                binding.search.requestFocus()
                searchViewModel.startSearch()
                imm.showSoftInput(binding.search, 0)
                binding.searchImage.setImageDrawable(resources.getDrawable(R.drawable.ic_delete))
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}