package com.crayonwriter.asteroidsarecoming.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crayonwriter.asteroidsarecoming.Asteroid
import com.crayonwriter.asteroidsarecoming.R
import com.crayonwriter.asteroidsarecoming.TextItemViewHolder

//This class will take a list of asteroids and adapt it to something recyclerview can display
class AsteroidAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var asteroidData = listOf<Asteroid>()
        set(value) {
         field = value
         notifyDataSetChanged()
        }
    override fun getItemCount() = asteroidData.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = asteroidData[position]
        holder.textView.text = item.codename
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.asteroid_item, parent, false) as TextView
        return TextItemViewHolder(view)
    }


}
