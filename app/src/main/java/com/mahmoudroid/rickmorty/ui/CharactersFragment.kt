package com.mahmoudroid.rickmorty.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudroid.rickmorty.R
import com.mahmoudroid.rickmorty.adapter.CharactersAdapter
import com.mahmoudroid.rickmorty.model.Characters
import com.mahmoudroid.rickmorty.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.characters_fragment.*
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "CharactersFragment"

class CharactersFragment : Fragment(R.layout.characters_fragment) {

    private lateinit var charactersAdapter: CharactersAdapter


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
        charactersRecyclerview.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
            addItemDecoration(decoration)
            Log.d(TAG, "####initRecyclerview: " + charactersAdapter.itemCount.toString())
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