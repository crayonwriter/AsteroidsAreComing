package com.crayonwriter.asteroidsarecoming.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crayonwriter.asteroidsarecoming.database.Asteroid
import com.crayonwriter.asteroidsarecoming.databinding.ListItemAsteroidBinding

//Changed to listAdapter instead. Lesson 2, exercise 13 Refresh Data with DiffUtil
//This class will take a list of asteroids and adapt it to something recyclerview can display
class AsteroidAdapter(val clickListener: AsteroidListener): androidx.recyclerview.widget.ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    //Extracted a function called bind. Exercise 10: Refactor onBindViewHolder.
    //Refactored the ViewHolder in the SleepNightAdapter. By encapsulating the logic in onBindViewHolder
    //Wire clickListener to the databinding
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    //Encapsulated the logic in onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemAsteroidBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, clickListener: AsteroidListener) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
//            binding.codenameString.text = item.codename
//            binding.dateString.text = item.closeApproachDate
//            binding.dangerImage.setImageResource(when (item.isPotentiallyHazardous) {
//                true -> R.drawable.ic_status_potentially_hazardous
//                false -> R.drawable.ic_status_normal
//            })
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                //Use binding object to inflate the layout. Replace layoutInflater with ListItemAsteroidBinding
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
                //Pass the binding to the ViewHolder constructor, and change its type
                return ViewHolder(binding)
            }
        }
    }
    }

//Created class AsteroidDiffCallback and implement the two methods comparing items and contents
class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.asteroidId == newItem.asteroidId
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
