package com.mahmoudroid.rickmorty.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mahmoudroid.rickmorty.R
import com.mahmoudroid.rickmorty.databinding.CharacterDetailsFragmentBinding
import com.mahmoudroid.rickmorty.databinding.CharactersFragmentBinding
import com.mahmoudroid.rickmorty.model.Characters
import com.mahmoudroid.rickmorty.ui.home.CharactersFragmentDirections

private const val TAG = "CharacterDetailsFragmen"
class CharacterDetailsFragment  : Fragment(R.layout.character_details_fragment){
    private lateinit var binding: CharacterDetailsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "CharacterDetailsFragment: ")
        binding = CharacterDetailsFragmentBinding.bind(view)

        val character = arguments?.getSerializable("character") as Characters

        binding.progressBar.visibility = View.VISIBLE

        Log.e(TAG, "onViewCreated: test " + character.name.toString())
        binding.characterName.text = character.name
        binding.characterStatus.text = "Status: ${character.status}"
        binding.characterSpecies.text = "Species: ${character.species}"
        binding.characterGender.text = "Gender: ${character.gender}"
        binding.characterOrigin.text = "Origin: ${character.origin.name}"
        binding.characterLocation.text = "Location: ${character.location.name}"
        binding.characterEpisodes.text = "Episodes: ${character.episode.size}"

        Glide.with(requireContext())
            .load(character.image)
            .into(binding.characterImage)

        binding.progressBar.visibility = View.GONE
    }

}