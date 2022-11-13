package com.example.asteriod.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteriod.Asteroid
import com.example.asteriod.databinding.AsteroidItemBinding

class AsteroidAdapter(val asteroidclick: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bindd(asteroid, asteroidclick)
    }


    class ViewHolder(val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun bindd(asteroid: Asteroid?, asteroidclick: AsteroidAdapter.AsteroidClickListener) {
            binding.asteroid = asteroid
            binding.asteroidOnClick = asteroidclick
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                // val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidClickListener(val asteroidclick: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = asteroidclick(asteroid)
    }

    class AsteroidDiffUtil : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }
}