package com.mahmoudroid.rickmorty.adapter

import android.util.Log
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

private const val TAG = "#####"

class CharactersAdapter :
    PagingDataAdapter<Characters, CharactersAdapter.MyViewHolder>
        (DiffUtilCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(
                R.layout.characters_list_item, parent, false
            )

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(getItem(position)!!)
        Log.d(TAG, "#####: " + holder.itemId.toString())
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.characterImage)
        val tvName: TextView = view.findViewById(R.id.characterName)
        val tvDesc: TextView = view.findViewById(R.id.characterDescription)

        fun bind(data: Characters) {
            tvName.text = data.name
            tvDesc.text = data.species
            Log.d(TAG, "bind: " + data.name.toString())
            Glide.with(imageView)
                .load(data.image)
                .circleCrop()
                .into(imageView)

        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.species == newItem.species
        }
    }
}