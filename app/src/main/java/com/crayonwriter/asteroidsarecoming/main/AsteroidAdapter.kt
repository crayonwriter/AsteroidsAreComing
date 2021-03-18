package com.crayonwriter.asteroidsarecoming.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crayonwriter.asteroidsarecoming.Asteroid
import com.crayonwriter.asteroidsarecoming.R

//Todo: Change to listAdapter instead. Lesson 2, exercise 13 Refresh Data with DiffUtil
//This class will take a list of asteroids and adapt it to something recyclerview can display
class AsteroidAdapter: RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {
    var asteroidData = listOf<Asteroid>()
        set(value) {
         field = value
         notifyDataSetChanged()
        }
    override fun getItemCount() = asteroidData.size

    //Extracted a function called bind. Exercise 10: Refactor onBindViewHolder.
    //Refactored the ViewHolder in the SleepNightAdapter. By encapsulating the logic in onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = asteroidData[position]
        holder.bind(item)
    }

    //Encapsulated the logic in onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val codename: TextView = itemView.findViewById(R.id.codename_string)
        val date: TextView = itemView.findViewById(R.id.date_string)
        val dangerImage: ImageView = itemView.findViewById(R.id.danger_image)

        fun bind(item: Asteroid) {
            codename.text = item.codename
            date.text = item.closeApproachDate
            dangerImage.setImageResource(when (item.isPotentiallyHazardous) {
                true -> R.drawable.ic_status_potentially_hazardous
                false -> R.drawable.ic_status_normal
            })
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_asteroid, parent, false)
                return ViewHolder(view)
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
