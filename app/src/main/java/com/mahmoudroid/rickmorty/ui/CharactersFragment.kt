package com.mahmoudroid.rickmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudroid.rickmorty.R
import com.mahmoudroid.rickmorty.adapter.CharactersAdapter
import com.mahmoudroid.rickmorty.databinding.CharactersFragmentBinding
import com.mahmoudroid.rickmorty.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "CharactersFragment"

class CharactersFragment : Fragment(R.layout.characters_fragment) {

    private lateinit var charactersAdapter: CharactersAdapter
    lateinit var binding: CharactersFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        initViewModel()

        charactersAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("character", it)
            }

            findNavController().navigate(
                R.id.action_charactersFragment_to_characterDetails,
                bundle
            )
        }
    }


    private fun initRecyclerview() {
        charactersAdapter = CharactersAdapter()

        binding.charactersRecyclerview.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                charactersAdapter.submitData(it)
            }
        }
    }
}