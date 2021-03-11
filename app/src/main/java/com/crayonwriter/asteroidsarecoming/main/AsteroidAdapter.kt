package com.crayonwriter.asteroidsarecoming.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crayonwriter.asteroidsarecoming.Asteroid
import com.crayonwriter.asteroidsarecoming.R
import com.crayonwriter.asteroidsarecoming.TextItemViewHolder
import org.w3c.dom.Text

//This class will take a list of asteroids and adapt it to something recyclerview can display
class AsteroidAdapter: RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {
    var asteroidData = listOf<Asteroid>()
        set(value) {
         field = value
         notifyDataSetChanged()
        }
    override fun getItemCount() = asteroidData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = asteroidData[position]
        holder.codename.text = item.toString()
        holder.date.text = item.toString()
                holder.dangerImage.setImageResource(when (item.isPotentiallyHazardous) {
            true -> R.drawable.asteroid_hazardous
            false -> R.drawable.asteroid_safe
    })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_asteroid, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val codename: TextView = itemView.findViewById(R.id.codename_text_view)
        val date: TextView = itemView.findViewById(R.id.date_string)
        val dangerImage: ImageView = itemView.findViewById(R.id.danger_image)
    }


}
