package com.mahmoudroid.rickmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoudroid.rickmorty.R
import com.mahmoudroid.rickmorty.model.Characters

class CharactersAdapter :
    PagingDataAdapter<Characters, CharactersAdapter.CharactersViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersAdapter.CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(
            R.layout.characters_list_item, parent, false
        )
        return CharactersViewHolder(inflater)
    }


    override fun onBindViewHolder(holder: CharactersAdapter.CharactersViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    class CharactersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val characterImage: ImageView = view.findViewById(R.id.characterImage)
        private val characterName: TextView = view.findViewById(R.id.characterName)
        private val characterDescription: TextView = view.findViewById(R.id.characterDescription)

        fun bind(character: Characters) {
            characterName.text = character.name
            characterDescription.text = character.species

            Glide.with(characterImage)
                .load(character.image)
                .circleCrop()
                .into(characterImage)
        }
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.species == newItem.species
        }

    }
}