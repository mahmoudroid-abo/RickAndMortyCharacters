package com.mahmoudroid.rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudroid.rickmorty.adapter.CharactersAdapter
import com.mahmoudroid.rickmorty.databinding.ActivityMainBinding
import com.mahmoudroid.rickmorty.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest


private const val TAG = "####MainActivity"

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        initViewModel()
    }

    private fun initRecyclerview() {

        binding.charactersRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
            addItemDecoration(decoration)

            charactersAdapter = CharactersAdapter()
            adapter = charactersAdapter
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